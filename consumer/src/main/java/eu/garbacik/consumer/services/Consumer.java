package eu.garbacik.consumer.services;

import eu.garbacik.common.settings.KafkaSettings;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @KafkaListener(id = "consumer-1", topics = "#{ @kafkaSettings.getTopic().getName() }")
    public void listen(String message){
        System.out.println(message);
    }
}
