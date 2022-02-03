package dachertanov.pal.palbackendservice.outbox;

import dachertanov.pal.palbackendservice.repository.RecommendationOutboxRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
public class OutboxConfiguration {
    private final String palWatchedAnimeTopicName;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RecommendationOutboxRepository recommendationOutboxRepository;

    public OutboxConfiguration(@Value("${kafka.topics.pal-backend-messages.name}") String palWatchedAnimeTopicName,
                               KafkaTemplate<String, Object> kafkaTemplate,
                               RecommendationOutboxRepository recommendationOutboxRepository) {
        this.palWatchedAnimeTopicName = palWatchedAnimeTopicName;
        this.kafkaTemplate = kafkaTemplate;
        this.recommendationOutboxRepository = recommendationOutboxRepository;
    }

    @Bean
    public OutboxEventsProducer outboxEventsProducer() {
        return new OutboxEventsProducer(palWatchedAnimeTopicName, kafkaTemplate);
    }

    @Bean
    public OutboxPublisher fmDriverOutboxPublisher() {
        return new OutboxPublisher(recommendationOutboxRepository, outboxEventsProducer());
    }

    @Bean
    public OutboxMessageRelay fmDriverOutboxMessageRelay() {
        return new OutboxMessageRelay(fmDriverOutboxPublisher());
    }
}
