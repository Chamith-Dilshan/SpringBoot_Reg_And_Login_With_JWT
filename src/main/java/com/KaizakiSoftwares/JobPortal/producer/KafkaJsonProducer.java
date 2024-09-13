package com.KaizakiSoftwares.JobPortal.producer;

import com.KaizakiSoftwares.JobPortal.payload.AuthResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaJsonProducer {

    private final KafkaTemplate<String, AuthResult> kafkaTemplate;

    public void sendJsonMessage(AuthResult authResult){

        Message<AuthResult> message = MessageBuilder
                .withPayload(authResult)
                .setHeader(KafkaHeaders.TOPIC,"validate_jwt_result")
                .build();

        kafkaTemplate.send(message);
    }
}
