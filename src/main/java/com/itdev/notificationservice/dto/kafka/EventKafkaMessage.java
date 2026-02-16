package com.itdev.notificationservice.dto.kafka;

import com.itdev.notificationservice.database.entity.EventStatus;

import java.time.LocalDateTime;
import java.util.Set;

public record EventKafkaMessage(
        Long eventId,
        Long changedBy,
        LocalDateTime changedAt,
        Long ownerId,
        EventModificationType eventModificationType,
        Set<FieldChange<?>> fieldChanges,
        FieldChange<EventStatus> status,
        Set<Long> attendeeIds
) {
}
