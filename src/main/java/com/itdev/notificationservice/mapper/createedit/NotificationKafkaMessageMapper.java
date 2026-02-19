package com.itdev.notificationservice.mapper.createedit;

import com.itdev.notificationservice.database.entity.FieldChangeEmbeddable;
import com.itdev.notificationservice.database.entity.RecipientCheckingInfo;
import com.itdev.notificationservice.database.entity.NotificationEntity;
import com.itdev.notificationservice.dto.kafka.EventKafkaMessage;
import com.itdev.notificationservice.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationKafkaMessageMapper implements Mapper<EventKafkaMessage, NotificationEntity> {

    private final FieldChangeCreateEditMapper fieldChangeCreateEditMapper;

    public NotificationKafkaMessageMapper(FieldChangeCreateEditMapper fieldChangeCreateEditMapper) {
        this.fieldChangeCreateEditMapper = fieldChangeCreateEditMapper;
    }

    @Override
    public NotificationEntity map(EventKafkaMessage fromObject) {
        NotificationEntity notificationEntity = new NotificationEntity();
        copy(fromObject, notificationEntity);
        return notificationEntity;
    }

    @Override
    public NotificationEntity map(EventKafkaMessage fromObject, NotificationEntity toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(EventKafkaMessage dto, NotificationEntity notificationEntity) {
        notificationEntity.setEventId(dto.eventId());
        notificationEntity.setChangedBy(dto.changedBy());
        notificationEntity.setChangedAt(dto.changedAt());
        notificationEntity.setOwnerId(dto.ownerId());
        notificationEntity.setEventModificationType(dto.eventModificationType());
        notificationEntity.setFieldChanges(dto.fieldChanges().stream()
                .map(fieldChangeCreateEditMapper::map)
                .collect(Collectors.toList()));
        if (dto.status() != null) notificationEntity.getFieldChanges().add(
                new FieldChangeEmbeddable<>(
                        dto.status().oldValue(),
                        dto.status().newValue(),
                        dto.status().fieldName(),
                        dto.status().fieldType().getName()
                )
        );
        notificationEntity.setRecipientCheckingInfos(buildAttendeeCheckingInfos(dto));
    }

    private List<RecipientCheckingInfo> buildAttendeeCheckingInfos(EventKafkaMessage dto) {
        ArrayList<RecipientCheckingInfo> infos = new ArrayList<>(dto.attendeeIds().size());
        dto.attendeeIds().stream()
                .map(id -> new RecipientCheckingInfo(id, false))
                .forEach(infos::add);
        infos.add(new RecipientCheckingInfo(dto.ownerId(), false));
        return infos;
    }
}
