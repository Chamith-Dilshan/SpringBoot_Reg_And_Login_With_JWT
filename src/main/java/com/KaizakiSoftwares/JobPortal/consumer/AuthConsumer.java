package com.KaizakiSoftwares.JobPortal.consumer;

import com.KaizakiSoftwares.JobPortal.auth.AuthenticationService;
import com.KaizakiSoftwares.JobPortal.payload.AuthPayLoad;
import com.KaizakiSoftwares.JobPortal.payload.AuthResult;
import com.KaizakiSoftwares.JobPortal.producer.KafkaJsonProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Slf4j
public class AuthConsumer {

    private AuthenticationService authenticationService;
    private KafkaJsonProducer kafkaJsonProducer;
    private AuthResult authResult;
    boolean isValid = false;
    
    @KafkaListener(topics = "validate_jwt", groupId = "authGroup")
    public void consumeJsonMsg(AuthPayLoad authPayLoad){
        if (authenticationService.validateToken(authPayLoad.getToken())) isValid = true;
        else isValid = false;

        authResult.setResult(isValid);
        authResult.setId(authPayLoad.getId());
        kafkaJsonProducer.sendJsonMessage(authResult);

        log.info(format("Consuming the Json message from validate_jwt :: %s",authPayLoad.toString()));
    }
}
