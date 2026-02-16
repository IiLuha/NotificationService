package com.itdev.notificationservice.http.rest;

import com.itdev.notificationservice.dto.AuthenticatedUser;
import com.itdev.notificationservice.dto.createedit.NotificationIds;
import com.itdev.notificationservice.dto.read.NotificationReadDto;
import com.itdev.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationRestController {

    public final NotificationService notificationService;

    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<Page<NotificationReadDto>> findAll(Pageable pageable,
                                                             @AuthenticationPrincipal AuthenticatedUser authenticated) {
        return ResponseEntity.ok(notificationService.findAllByAttendee(authenticated, pageable));
    }

    @PutMapping
    public ResponseEntity<Integer> markAsRead(
            @AuthenticationPrincipal AuthenticatedUser authenticated,
            @RequestBody @Valid NotificationIds notificationIds) {
        int updatedLines = notificationService.markAsReadRecipientsNotifications(authenticated, notificationIds.ids());
        return updatedLines == notificationIds.ids().size() ?
                ResponseEntity.ok(updatedLines) :
                ResponseEntity.internalServerError().body(updatedLines);
    }
}
