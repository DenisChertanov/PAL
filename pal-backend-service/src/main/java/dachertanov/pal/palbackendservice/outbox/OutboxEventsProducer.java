package dachertanov.pal.palbackendservice.outbox;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.mapping.AbstractJavaTypeMapper;

import java.nio.charset.StandardCharsets;

@Slf4j
@AllArgsConstructor
public class OutboxEventsProducer {
    private final String topicName;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEvent(String key, Object body, Class<?> type) {
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topicName, key, body);
        producerRecord
                .headers()
                .add(AbstractJavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME,
                        type.getName().getBytes(StandardCharsets.UTF_8));

        kafkaTemplate.send(producerRecord);
        log.info("Send kafka event " + body);
    }
}
