package com.itdev.notificationservice.service;

import com.itdev.notificationservice.dto.AuthenticatedUser;
import com.itdev.notificationservice.dto.JwtRequest;
import com.itdev.notificationservice.http.client.AuthServiceClient;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import java.util.Optional;

@Service
public class JwtAuthenticationService {

    private final SecretKey key;
    private final ObjectMapper objectMapper;
    private final AuthServiceClient authServiceClient;

    public JwtAuthenticationService(@Value("${jwt.secret-key}") String key,
                                    ObjectMapper objectMapper,
                                    AuthServiceClient authServiceClient) {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
        this.objectMapper = objectMapper;
        this.authServiceClient = authServiceClient;
    }

    public boolean validateJwtToken(String token) {
        try {
            ResponseEntity<Boolean> isValidResponse = authServiceClient.validateToken(new JwtRequest(token));
            return isValidResponse != null && Boolean.TRUE.equals(isValidResponse.getBody());
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<UserDetails> extractUser(String jwtToken) {
        try {
            String subject = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload()
                    .getSubject();

            return Optional.ofNullable(objectMapper.readValue(subject, AuthenticatedUser.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
