package com.itdev.notificationservice.mapper.read;

import com.itdev.notificationservice.database.entity.FieldChangeEmbeddable;
import com.itdev.notificationservice.dto.kafka.FieldChange;
import org.springframework.stereotype.Component;

@Component
public class FieldChangeReadMapper {

    public <T> FieldChange<T> map(FieldChangeEmbeddable<T> entity) {
        return new FieldChange<>(
                entity.getOldValue(),
                entity.getNewValue(),
                entity.getFieldName(),
                entity.getValueClass()
        );
    }
}
