package com.itdev.notificationservice.http.client;

import com.itdev.notificationservice.dto.JwtRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "event-manager",
        url = "${event-manager.url}"
)
public interface AuthServiceClient {

    @PostMapping("/api/v1/auth/validate")
    ResponseEntity<Boolean> validateToken(@RequestBody JwtRequest jwtRequest);
}
