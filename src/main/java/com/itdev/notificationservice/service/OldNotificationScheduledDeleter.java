package com.itdev.notificationservice.service;

import com.itdev.notificationservice.database.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class OldNotificationScheduledDeleter {

    private final NotificationRepository notificationRepository;

    public OldNotificationScheduledDeleter(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Scheduled(cron = "${event.stats.cron}")
    public void deleteOldNotifications() {
        notificationRepository.deleteAllByChangedAtBefore(LocalDateTime.now().minusWeeks(1L));
    }
}
