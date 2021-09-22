package eu.garbacik.consumer.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @KafkaListener(id = "consumer-1", topics = "#{ @kafkaSettings.getTopic().getName() }")
    public void listen(String message){
        log.info("Message received: " + message);
    }
}
