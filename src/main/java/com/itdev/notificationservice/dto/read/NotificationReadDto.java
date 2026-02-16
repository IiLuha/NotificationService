package com.itdev.notificationservice.dto.read;

import com.itdev.notificationservice.dto.kafka.EventModificationType;
import com.itdev.notificationservice.dto.kafka.FieldChange;

import java.time.LocalDateTime;
import java.util.List;

public record NotificationReadDto(
        Long id,
        Long eventId,
        Long changedBy,
        LocalDateTime changedAt,
        Long ownerId,
        EventModificationType eventModificationType,
        List<FieldChange<?>> fieldChanges
) {
}
