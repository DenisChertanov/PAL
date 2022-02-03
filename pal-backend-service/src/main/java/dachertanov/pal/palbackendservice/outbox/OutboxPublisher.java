package dachertanov.pal.palbackendservice.outbox;

import dachertanov.pal.palbackendservice.entity.RecommendationOutbox;
import dachertanov.pal.palbackendservice.repository.RecommendationOutboxRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class OutboxPublisher {
    private final RecommendationOutboxRepository recommendationOutboxRepository;
    private final OutboxEventsProducer outboxEventsProducer;

    @Transactional
    public int publicNextBatch() {
        List<RecommendationOutbox> topRecords = recommendationOutboxRepository.getTop10BySentTimeIsNull();
        int counter = 0;
        for (RecommendationOutbox record : topRecords) {
            outboxEventsProducer.sendEvent(record.getAggregateId(), record.getPayload());
            record.setSentTime(LocalDateTime.now());
            recommendationOutboxRepository.save(record);
            counter++;
        }
        return counter;
    }
}
