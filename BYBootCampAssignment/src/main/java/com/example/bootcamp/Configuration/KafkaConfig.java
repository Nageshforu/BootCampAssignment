package com.example.bootcamp.Configuration;

import com.example.bootcamp.model.EmployeeInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConfig {
    private  static final String TOPIC = "app_updates";

    @Autowired
    private final KafkaTemplate<String, String> template;

    public KafkaConfig(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void writeMessage(String employeeInformation) {
        this.template.send(TOPIC, employeeInformation);
    }

    @KafkaListener(topics = "app_updates", groupId="my_group_id",
            containerFactory = "kafkaListenerContainerFactory")
    public  void getMessage(String employeeInformation) throws JsonProcessingException {
        System.out.println("app_updates topic: " + employeeInformation);
        ObjectMapper objectMapper = new ObjectMapper();
        EmployeeInformation information = objectMapper.readValue(employeeInformation, EmployeeInformation.class);
        if(null == information.getEmp_name() || 0 == information.getEmp_id() ||
            null == information.getEmp_city() || null == information.getEmp_phone() ||
            0 == information.getJava_exp() || 0 == information.getSpring_exp()) {
            this.template.send("employee_DLQ", employeeInformation);
        } else {
            this.template.send("employee_updates", employeeInformation);
        }
    }
    @KafkaListener(topics = "employee_updates", groupId="my_group_id",
            containerFactory = "kafkaListenerContainerFactory")
    public  void employeeUpdate(String employeeInformation) {
        System.out.println("employee_updates topic: "+employeeInformation);
    }

    @KafkaListener(topics = "employee_DLQ", groupId="my_group_id",
            containerFactory = "kafkaListenerContainerFactory")
    public  void inValidEmployeeUpdate(String employeeInformation) {
        System.out.println("employee_DLQ topic: "+employeeInformation);
    }

}
