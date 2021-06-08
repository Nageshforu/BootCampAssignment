package com.example.bootcamp.Configuration;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerConfig {

    @KafkaListener(topics = "app_updates", groupId="my_group_id")
    public  void getMessage(String message) {
        System.out.println(message);
    }
}
