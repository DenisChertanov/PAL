package dachertanov.pal.palbackendservice.kafka.recommendation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

/**
 * Конфигурация работы с топиком PAL.Messages для отправки сообщений в pal-recommendation-service
 */
@Configuration
public class WatchedAnimeConfig {
    private final String palWatchedAnimeTopicName;
    private final int numPartitions;
    private final short replicationFactor;
    private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;

    public WatchedAnimeConfig(@Value("${kafka.topics.pal-backend-messages.name}") String palWatchedAnimeTopicName,
                              @Value("${kafka.topics.pal-backend-messages.numPartitions}") int numPartitions,
                              @Value("${kafka.topics.pal-backend-messages.replicationFactor}") short replicationFactor,
                              KafkaProperties kafkaProperties,
                              ObjectMapper objectMapper) {
        this.palWatchedAnimeTopicName = palWatchedAnimeTopicName;
        this.numPartitions = numPartitions;
        this.replicationFactor = replicationFactor;
        this.kafkaProperties = kafkaProperties;
        this.objectMapper = objectMapper;
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        return kafkaProperties.buildProducerProperties();
    }

    /**
     * Маппер java-типа на тип события
     */
    @Bean
    public Jackson2JavaTypeMapper jackson2JavaTypeMapper() {
        return new PredefinedTypeJackson2JavaTypeMapper();
    }

    @Bean
    public JsonSerializer<Object> jsonValueSerializer() {
        JsonSerializer<Object> jsonSerializer = new JsonSerializer<>(objectMapper);
        jsonSerializer.setTypeMapper(jackson2JavaTypeMapper());
        return jsonSerializer;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), null, jsonValueSerializer());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        var kafkaTemplate = new KafkaTemplate<>(producerFactory());
        kafkaTemplate.setDefaultTopic(palWatchedAnimeTopicName);
        return kafkaTemplate;
    }

    @Bean
    public NewTopic palWatchedAnimeTopic() {
        return new NewTopic(palWatchedAnimeTopicName, numPartitions, replicationFactor);
    }
}
