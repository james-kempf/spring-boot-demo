package com.example.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send("quickstart-events", message).thenAccept(sr -> {
            System.out.println(sr.toString());
        });
        System.out.println("Message Sent: " + message);
    }

    @KafkaListener(topics = "quickstart-events")
    private void readMessage(String message) {
        System.out.println("Message Consumed: " + message);
    }
}
