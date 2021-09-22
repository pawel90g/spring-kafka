package eu.garbacik.consumer.services;

import lombok.extern.slf4j.Slf4j;
import org.intellij.lang.annotations.Language;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @KafkaListener(id = "consumer-1", topics = "#{ @kafkaSettings.getTopic().getName() }")
    public void listen(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Message received: " + message +
                ", partition: " + partition +
                ", offset: " + offset);
    }

    @KafkaListener(id = "consumer-2", topics = "#{ @kafkaSettings.getReplyTopic().getName() }")
    @SendTo("#{ @kafkaSettings.getReplyTopic().getReplyName() }")
    public String listenAndReply(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Message received: " + message +
                ", partition: " + partition +
                ", offset: " + offset);

        return "This is response for message: " + message;
    }
}
