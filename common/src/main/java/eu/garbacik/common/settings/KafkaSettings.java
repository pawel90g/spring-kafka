package eu.garbacik.common.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "kafka")
public class KafkaSettings {
    private String bootstrapAddress;
    private TopicSettings topic;
    private TopicWithReplySettings replyTopic;
}
