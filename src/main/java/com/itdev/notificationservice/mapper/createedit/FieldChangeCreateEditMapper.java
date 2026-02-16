package com.itdev.notificationservice.mapper.createedit;

import com.itdev.notificationservice.database.entity.EventStatus;
import com.itdev.notificationservice.database.entity.FieldChangeEmbeddable;
import com.itdev.notificationservice.dto.kafka.FieldChange;
import org.springframework.stereotype.Component;

@Component
public class FieldChangeCreateEditMapper {

    public <T> FieldChangeEmbeddable<?> map(FieldChange<T> fromObject) {
        if (fromObject.fieldName().equals("status") && fromObject.fieldType().equals(String.class)) {
            FieldChangeEmbeddable<EventStatus> fieldChangeEmbeddable = new FieldChangeEmbeddable<>();
            copyStatus((FieldChange<String>) fromObject, fieldChangeEmbeddable);
            return fieldChangeEmbeddable;
        } else {
            FieldChangeEmbeddable<T> fieldChangeEmbeddable = new FieldChangeEmbeddable<>();
            copy(fromObject, fieldChangeEmbeddable);
            return fieldChangeEmbeddable;
        }

    }

    private void copyStatus(FieldChange<String> dto, FieldChangeEmbeddable<EventStatus> fieldChangeEmbeddable) {
        if (dto.oldValue() != null) fieldChangeEmbeddable.setOldValue(EventStatus.valueOf(dto.oldValue()));
        if (dto.newValue() != null) fieldChangeEmbeddable.setNewValue(EventStatus.valueOf(dto.newValue()));
        fieldChangeEmbeddable.setFieldName(dto.fieldName());
        fieldChangeEmbeddable.setFieldTypeName(EventStatus.class.getName());
    }

    private <T> void copy(FieldChange<T> dto, FieldChangeEmbeddable<T> fieldChangeEmbeddable) {
        fieldChangeEmbeddable.setOldValue(dto.oldValue());
        fieldChangeEmbeddable.setNewValue(dto.newValue());
        fieldChangeEmbeddable.setFieldName(dto.fieldName());
        fieldChangeEmbeddable.setFieldTypeName(dto.fieldType().getName());
    }
}
