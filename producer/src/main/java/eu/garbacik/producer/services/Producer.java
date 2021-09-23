package eu.garbacik.producer.services;

import eu.garbacik.common.settings.KafkaSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
@EnableConfigurationProperties(KafkaSettings.class)
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplateWithListener;
    private final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;
    private final KafkaSettings kafkaSettings;

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplateWithListener,
                    ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate,
                    KafkaSettings kafkaSettings) {
        this.kafkaTemplateWithListener = kafkaTemplateWithListener;
        this.replyingKafkaTemplate = replyingKafkaTemplate;
        this.kafkaSettings = kafkaSettings;
    }

    public void sendMessage(String msg){
        kafkaTemplateWithListener.send(kafkaSettings.getTopic().getName(), msg);
    }

    public void sendMessageWithCallback(String msg) {
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplateWithListener.send(kafkaSettings.getTopic().getName(), msg);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.warn("Unable to deliver message [{}]. {}",
                        msg,
                        throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> sendResult) {
                log.info("Message [{}] delivered with offset {}",
                        msg,
                        sendResult.getRecordMetadata().offset());
            }
        });
    }

    public void sendMessageAndReadResponse(String message)
            throws ExecutionException, InterruptedException {

        var record =
                new ProducerRecord<String, String>(kafkaSettings.getReplyTopic().getName(), message);

        RequestReplyFuture<String, String, String> future =
                replyingKafkaTemplate.sendAndReceive(record);

        ConsumerRecord<String, String> consumerRecord = future.get();
        log.info("Reply received: {}", consumerRecord.value());
    }
}
