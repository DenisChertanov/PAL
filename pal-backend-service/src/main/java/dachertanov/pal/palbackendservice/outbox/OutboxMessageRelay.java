package dachertanov.pal.palbackendservice.outbox;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@AllArgsConstructor
public class OutboxMessageRelay {
    private final OutboxPublisher outboxPublisher;

    @Scheduled(fixedDelay = 1000)
    public void pollOutbox() {
        int publishedCurrentBatch;
        do {
            publishedCurrentBatch = outboxPublisher.publicNextBatch();
        } while (publishedCurrentBatch != 0);
    }
}
