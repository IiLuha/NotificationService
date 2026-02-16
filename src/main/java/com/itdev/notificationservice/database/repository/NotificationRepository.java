package com.itdev.notificationservice.database.repository;

import com.itdev.notificationservice.database.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @Query("SELECT n FROM NotificationEntity n " +
            "JOIN n.recipientCheckingInfos r " +
            "WHERE r.recipientId = :recipientId AND r.isRead = false")
    Page<NotificationEntity> findAllUnreadByRecipientId(
            @Param("recipientId") Long recipientId,
            Pageable pageable
    );

    @Modifying
    @Query(value =
            "UPDATE notification_recipient " +
            "SET is_read = true " +
            "WHERE recipient_id = :recipientId " +
            "AND notification_id = ANY(:notificationIds)",
            nativeQuery = true)
    int markAsReadRecipientsNotifications(
            @Param("recipientId") Long recipientId,
            @Param("notificationIds") Long[] notificationIds
    );

    void deleteAllByChangedAtBefore(LocalDateTime changedAtBefore);

    boolean existsByChangedAtAndEventIdAndChangedBy(LocalDateTime changedAt, Long eventId, Long changedBy);
}
