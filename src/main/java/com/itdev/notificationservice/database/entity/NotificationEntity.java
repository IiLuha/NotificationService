package com.itdev.notificationservice.database.entity;

import com.itdev.notificationservice.dto.kafka.EventModificationType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notification",
        uniqueConstraints = @UniqueConstraint(columnNames = {"changed_at", "event_id", "changed_by"}))
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "event_id")
    Long eventId;

    @Column(name = "changed_by")
    Long changedBy;

    @Column(name = "changed_at")
    LocalDateTime changedAt;

    @Column(name = "owner_id")
    Long ownerId;

    @Column(name = "kafka_event_type")
    EventModificationType eventModificationType;

    @ElementCollection
    @CollectionTable(
            name = "field_changes",
            joinColumns = @JoinColumn(name = "notification_id")
    )
    List<FieldChangeEmbeddable<?>> fieldChangeEmbeddables = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "notification_recipient",
            joinColumns = @JoinColumn(name = "notification_id")
    )
    List<RecipientCheckingInfo> recipientCheckingInfos = new ArrayList<>();

    public NotificationEntity() {
    }

    public NotificationEntity(Long id, Long eventId, Long changedBy, Long ownerId, EventModificationType eventModificationType,
                              List<FieldChangeEmbeddable<?>> fieldChangeEmbeddables, List<RecipientCheckingInfo> recipientCheckingInfos) {
        this.id = id;
        this.eventId = eventId;
        this.changedBy = changedBy;
        this.ownerId = ownerId;
        this.eventModificationType = eventModificationType;
        this.fieldChangeEmbeddables = fieldChangeEmbeddables;
        this.recipientCheckingInfos = recipientCheckingInfos;
    }


    @Override
    public String toString() {
        return "NotificationEntity{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", changedBy=" + changedBy +
                ", ownerId=" + ownerId +
                ", eventModificationType=" + eventModificationType +
                ", fieldChanges=" + fieldChangeEmbeddables +
                ", recipientCheckingInfos=" + recipientCheckingInfos +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Long changedBy) {
        this.changedBy = changedBy;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public EventModificationType getEventModificationType() {
        return eventModificationType;
    }

    public void setEventModificationType(EventModificationType eventModificationType) {
        this.eventModificationType = eventModificationType;
    }

    public List<FieldChangeEmbeddable<?>> getFieldChanges() {
        return fieldChangeEmbeddables;
    }

    public void setFieldChanges(List<FieldChangeEmbeddable<?>> fieldChangeEmbeddables) {
        this.fieldChangeEmbeddables = fieldChangeEmbeddables;
    }

    public List<RecipientCheckingInfo> getRecipientCheckingInfos() {
        return recipientCheckingInfos;
    }

    public void setRecipientCheckingInfos(List<RecipientCheckingInfo> recipientCheckingInfos) {
        this.recipientCheckingInfos = recipientCheckingInfos;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
}
