package eu.garbacik.producer.services;

import eu.garbacik.common.settings.KafkaSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(KafkaSettings.class)
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaSettings kafkaSettings;

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate,
                    KafkaSettings kafkaSettings) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaSettings = kafkaSettings;
    }

    public void sendMessage(String msg){
        kafkaTemplate.send(kafkaSettings.getTopic().getName(), msg);
    }
}
