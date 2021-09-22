package eu.garbacik.producer.configuration;

import eu.garbacik.common.settings.KafkaSettings;
import eu.garbacik.common.settings.TopicSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(KafkaSettings.class)
@Slf4j
public class KafkaConfig {

    private final KafkaSettings kafkaSettings;

    @Autowired
    public KafkaConfig(KafkaSettings kafkaSettings) {
        this.kafkaSettings = kafkaSettings;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaSettings.getBootstrapAddress());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic() {

        TopicSettings topicSettings = kafkaSettings.getTopic();

        return TopicBuilder
                .name(topicSettings.getName())
                .partitions(topicSettings.getPartitionsCount())
                .replicas(topicSettings.getReplicasCount())
                .build();
    }
}
