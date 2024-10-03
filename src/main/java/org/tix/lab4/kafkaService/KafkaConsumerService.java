package org.tix.lab4.kafkaService;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.tix.lab4.model.ExpertMessage;
import org.tix.lab4.service.CreditService;

@Service
public class KafkaConsumerService {
    private final CreditService creditService;

    public KafkaConsumerService(CreditService creditService) {
        this.creditService = creditService;
    }

    @KafkaListener(topics = "expertResponse", groupId = "my-group", containerFactory = "userKafkaListenerContainerFactory")
    public void receiveMessage(ExpertMessage expertMessage) {
        creditService.loadExpertMessageFromAudition(expertMessage);
        System.out.println(expertMessage);

    }

}
