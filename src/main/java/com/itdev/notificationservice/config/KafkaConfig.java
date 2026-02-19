package com.itdev.notificationservice.config;

import com.itdev.notificationservice.dto.kafka.EventKafkaMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<Long, EventKafkaMessage> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        configProps.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "Notification-service-group");
        configProps.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LongDeserializer.class);
        configProps.put(JacksonJsonDeserializer.TRUSTED_PACKAGES,
                "com.itdev.finalproject.database.entity,com.itdev.notificationservice.database.entity");
        var factory = new DefaultKafkaConsumerFactory<Long, EventKafkaMessage>(configProps);
        factory.setValueDeserializer(new JacksonJsonDeserializer<>(EventKafkaMessage.class, false));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, EventKafkaMessage> containerFactory(
            ConsumerFactory<Long, EventKafkaMessage> consumerFactory
    ) {
        var containerFactory = new ConcurrentKafkaListenerContainerFactory<Long, EventKafkaMessage>();
        containerFactory.setConsumerFactory(consumerFactory);
        return containerFactory;
    }
}
