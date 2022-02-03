package dachertanov.pal.palbackendservice.outbox;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@AllArgsConstructor
public class OutboxEventsProducer {
    private final String topicName;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEvent(String key, Object body) {
        kafkaTemplate.send(topicName, key, body);
        log.info("Send kafka event " + body);
    }
}
