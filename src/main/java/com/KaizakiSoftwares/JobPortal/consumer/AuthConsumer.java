package com.KaizakiSoftwares.JobPortal.consumer;

import com.KaizakiSoftwares.JobPortal.auth.AuthenticationService;
import com.KaizakiSoftwares.JobPortal.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthConsumer {

    private AuthenticationService authenticationService;
    private KafkaProducer kafkaProducer;
    
//    @KafkaListener(topics = "validate_jwt", groupId = "authGroup")
//    public void consumeJsonMsg(AuthPayLoad authPayLoad2){
//        boolean isValid = authenticationService.validateToken(authPayLoad2.getToken());
//
//        AuthResult authResult = new AuthResult();
//        authResult.setResult(isValid);
//        authResult.setId(authPayLoad2.getId());
//        kafkaJsonProducer.sendJsonMessage(authResult);
//
//        log.info(format("Consuming the Json message from validate_jwt :: %s", authPayLoad2.toString()));
//    }
@KafkaListener(topics = "validate_jwt", groupId = "authGroup")
public void consumeMsg(String msg){
    kafkaProducer.sendMessage("true");
    log.info(format("Consuming the message from chamithTopic :: %s",msg));

}
}
