package com.example.bootcamp.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerConfig {

    private  static final String TOPIC = "app_updates";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void writeMessage(String message) {

        this.kafkaTemplate.send(TOPIC, message);
    }
}
