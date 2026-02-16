package com.itdev.notificationservice.mapper.read;

import com.itdev.notificationservice.database.entity.NotificationEntity;
import com.itdev.notificationservice.dto.read.NotificationReadDto;
import com.itdev.notificationservice.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class NotificationReadMapper implements Mapper<NotificationEntity, NotificationReadDto> {

    private final FieldChangeReadMapper fieldChangeReadMapper;

    public NotificationReadMapper(FieldChangeReadMapper fieldChangeReadMapper) {
        this.fieldChangeReadMapper = fieldChangeReadMapper;
    }

    @Override
    public NotificationReadDto map(NotificationEntity entity) {
        return new NotificationReadDto(
                entity.getId(),
                entity.getEventId(),
                entity.getChangedBy(),
                entity.getChangedAt(),
                entity.getOwnerId(),
                entity.getEventModificationType(),
                entity.getFieldChanges().stream()
                        .map(fieldChangeReadMapper::map)
                        .collect(Collectors.toList())
        );
    }
}
