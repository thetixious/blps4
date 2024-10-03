package org.tix.lab4.kafkaService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tix.lab4.model.ExpertMessage;

@Service
public class KafkaProducerService {

    @Value("${spring.kafka.template.default-topic}")
    private String topicName;
    private final KafkaTemplate<String, ExpertMessage> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, ExpertMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ExpertMessage message) {
        kafkaTemplate.send(topicName, message);
    }
}
