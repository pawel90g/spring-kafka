package eu.garbacik.consumer.services;

import eu.garbacik.common.messages.Message;
import lombok.extern.slf4j.Slf4j;
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

    @KafkaListener(id="consumer-json",
            topics = "#{ @kafkaSettings.getTopic().getName() }",
            containerFactory="messageKafkaListenerContainerFactory")
    public void listenForMessage(Message message){
        log.info("JSON Message received: id: {}, name: {}, active: {}",
                message.getId(),
                message.getName(),
                message.getActive());
    }
}
