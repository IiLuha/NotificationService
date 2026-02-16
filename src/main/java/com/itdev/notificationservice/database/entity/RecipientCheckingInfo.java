package com.itdev.notificationservice.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class RecipientCheckingInfo {

    @Column(name = "recipient_id")
    private Long recipientId;

    @Column(name = "is_read")
    private Boolean isRead;

    public RecipientCheckingInfo() {
    }

    public RecipientCheckingInfo(Long recipientId, Boolean isRead) {
        this.recipientId = recipientId;
        this.isRead = isRead;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RecipientCheckingInfo that = (RecipientCheckingInfo) o;
        return Objects.equals(recipientId, that.recipientId) && Objects.equals(isRead, that.isRead);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipientId, isRead);
    }

    @Override
    public String toString() {
        return "AttendeeCheckingInfo{" +
                "attendeeId=" + recipientId +
                ", isRead=" + isRead +
                '}';
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
