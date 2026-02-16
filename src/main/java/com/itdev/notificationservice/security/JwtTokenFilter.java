package com.itdev.notificationservice.security;

import com.itdev.notificationservice.service.JwtAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtAuthenticationService jwtAuthenticationService;

    public JwtTokenFilter(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = header.substring(7);
        UserDetails user;

        try {
            boolean isValid = jwtAuthenticationService.validateJwtToken(jwtToken);
            if (isValid) {
                Optional<UserDetails> maybeUser = jwtAuthenticationService.extractUser(jwtToken);
                if (maybeUser.isEmpty()) {
                    filterChain.doFilter(request, response);
                    return;
                }
                user = maybeUser.get();
            } else {
                filterChain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }
}
