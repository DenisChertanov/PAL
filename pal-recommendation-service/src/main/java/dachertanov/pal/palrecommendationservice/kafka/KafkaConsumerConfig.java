package dachertanov.pal.palrecommendationservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConsumerConfig {
    private final String palBackendMessagesTopicName;
    private final int numPartitions;
    private final short replicationFactor;
    private final ObjectMapper objectMapper;

    public KafkaConsumerConfig(@Value("${kafka.topics.pal-backend-messages.name}") String palBackendMessagesTopicName,
                               @Value("${kafka.topics.pal-backend-messages.numPartitions}") int numPartitions,
                               @Value("${kafka.topics.pal-backend-messages.replicationFactor}") short replicationFactor,
                               ObjectMapper objectMapper) {
        this.palBackendMessagesTopicName = palBackendMessagesTopicName;
        this.numPartitions = numPartitions;
        this.replicationFactor = replicationFactor;
        this.objectMapper = objectMapper;
    }

    @Bean
    public JsonSerializer<Object> jsonValueSerializer() {
        return new JsonSerializer<>(objectMapper);
    }

    @Bean
    public NewTopic smsAdapterMessagesTopic() {
        return new NewTopic(palBackendMessagesTopicName, numPartitions, replicationFactor);
    }
}
