package com.lbraz.strproducer.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class StringProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send("str-topic", message)
                .thenAccept(result -> {
                    log.info("Message sent successfully: " + message);
                    log.info("Topic: " + result.getRecordMetadata().topic() +
                            ", Partition: " + result.getRecordMetadata().partition() +
                            ", Offset: " + result.getRecordMetadata().offset());
                })
                .exceptionally(ex -> {
                    log.error("Failed to send message: " + message + ", error: " + ex.getMessage());
                    return null;
                });
    }
}
