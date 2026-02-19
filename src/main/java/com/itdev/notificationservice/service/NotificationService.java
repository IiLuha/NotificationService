package com.itdev.notificationservice.service;

import com.itdev.notificationservice.database.repository.NotificationRepository;
import com.itdev.notificationservice.dto.AuthenticatedUser;
import com.itdev.notificationservice.dto.kafka.EventKafkaMessage;
import com.itdev.notificationservice.dto.read.NotificationReadDto;
import com.itdev.notificationservice.mapper.createedit.NotificationKafkaMessageMapper;
import com.itdev.notificationservice.mapper.read.NotificationReadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationReadMapper notificationReadMapper;
    private final NotificationKafkaMessageMapper notificationKafkaMessageMapper;

    public NotificationService(NotificationRepository notificationRepository,
                               NotificationReadMapper notificationReadMapper, NotificationKafkaMessageMapper notificationKafkaMessageMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationReadMapper = notificationReadMapper;
        this.notificationKafkaMessageMapper = notificationKafkaMessageMapper;
    }

    public Page<NotificationReadDto> findAllByAttendee(AuthenticatedUser authenticated, Pageable pageable) {
        return notificationRepository.findAllUnreadByRecipientId(authenticated.getId(), pageable)
                .map(notificationReadMapper::map);
    }

    public int markAsReadRecipientsNotifications(AuthenticatedUser authenticated, List<Long> notificationIds) {
        return notificationRepository.markAsReadRecipientsNotifications(authenticated.getId(), notificationIds.toArray(Long[]::new));
    }

    public NotificationReadDto create(EventKafkaMessage message) {
        return Optional.of(message)
                .map(notificationKafkaMessageMapper::map)
                .map(notificationRepository::saveAndFlush)
                .map(notificationReadMapper::map)
                .orElseThrow();
    }

    public boolean notExist(Long eventId, Long changedBy, LocalDateTime changedAt) {
        return !notificationRepository.existsByChangedAtAndEventIdAndChangedBy(changedAt, eventId, changedBy);
    }
}
