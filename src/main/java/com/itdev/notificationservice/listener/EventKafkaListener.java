package com.itdev.notificationservice.listener;

import com.itdev.notificationservice.dto.kafka.EventKafkaMessage;
import com.itdev.notificationservice.service.NotificationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventKafkaListener {

    private final NotificationService notificationService;

    public EventKafkaListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "event-topic", containerFactory = "containerFactory")
    public void listenMessage(ConsumerRecord<Long, EventKafkaMessage> record) {
        EventKafkaMessage message = record.value();
        System.err.println("Kafka listen " + message);
        if (notificationService.notExist(message.eventId(), message.changedBy(), message.changedAt())) {
            notificationService.create(message);
        } else {
            System.err.printf("Already processed eventId=%s, changedBy=%s!, changedAt=%s%n",
                    message.eventId(), message.changedBy(), message.changedAt());
        }
    }
}
