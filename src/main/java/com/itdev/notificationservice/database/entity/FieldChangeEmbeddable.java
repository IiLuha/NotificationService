package com.itdev.notificationservice.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;

@Embeddable
public class FieldChangeEmbeddable<T> {

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private T oldValue;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private T newValue;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_type")
    private String fieldTypeName;

    public FieldChangeEmbeddable() {
    }

    public FieldChangeEmbeddable(T oldValue, T newValue, String fieldName, String fieldTypeName) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.fieldName = fieldName;
        this.fieldTypeName = fieldTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FieldChangeEmbeddable<?> that = (FieldChangeEmbeddable<?>) o;
        return Objects.equals(oldValue, that.oldValue) && Objects.equals(newValue, that.newValue) && Objects.equals(fieldName, that.fieldName) && Objects.equals(fieldTypeName, that.fieldTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldValue, newValue, fieldName, fieldTypeName);
    }

    @Override
    public String toString() {
        return "FieldChange{" +
                "oldValue=" + oldValue +
                ", newValue=" + newValue +
                ", fieldName='" + fieldName + '\'' +
                ", fieldTypeName='" + fieldTypeName + '\'' +
                '}';
    }

    public T getOldValue() {
        return oldValue;
    }

    public void setOldValue(T oldValue) {
        this.oldValue = oldValue;
    }

    public T getNewValue() {
        return newValue;
    }

    public void setNewValue(T newValue) {
        this.newValue = newValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTypeName() {
        return fieldTypeName;
    }

    public void setFieldTypeName(String fieldTypeName) {
        this.fieldTypeName = fieldTypeName;
    }

    public Class<T> getValueClass() {
        try {
            if (fieldTypeName == null)
                throw new RuntimeException("Not avaleable unknown class. fieldTypeName must not be null");
            return (Class<T>) Class.forName(fieldTypeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + fieldTypeName, e);
        } catch (ClassCastException e) {
            throw new RuntimeException("fieldType doesn't match: " + fieldTypeName, e);
        }
    }
}