package com.itdev.notificationservice.config;

import com.itdev.notificationservice.security.CustomAuthenticationEntryPoint;
import com.itdev.notificationservice.security.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
public class SecurityFilterChainConfig {

    private final JwtTokenFilter filter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityFilterChainConfig(JwtTokenFilter filter,
                                     CustomAuthenticationEntryPoint authenticationEntryPoint) {
        this.filter = filter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .requestMatchers("/v3/api-docs", "/swagger-ui/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/notifications")
                        .authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/notifications")
                        .authenticated()
                        .anyRequest().authenticated())

                .addFilterBefore(filter, AnonymousAuthenticationFilter.class)
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(authenticationEntryPoint));
        return http.build();
    }
}
