package dachertanov.pal.palrecommendationservice.kafka;

import dachertanov.pal.palrecommendationdto.WatchedAnime;
import dachertanov.pal.palrecommendationservice.service.RecommendationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaEventsListener {
    private final RecommendationService recommendationService;

    @KafkaListener(topics = "${kafka.topics.pal-backend-messages.name}")
    public void consume(WatchedAnime watchedAnime, Acknowledgment acknowledgment) {
        try {
            recommendationService.updateRecommendation(watchedAnime);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            acknowledgment.nack(30000);
        }
    }
}
