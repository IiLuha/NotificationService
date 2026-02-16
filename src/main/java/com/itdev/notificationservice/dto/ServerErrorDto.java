package com.itdev.notificationservice.dto;

public record ServerErrorDto(
        String message,
        String details
) {
}
