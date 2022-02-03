package dachertanov.pal.palbackendservice.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import dachertanov.pal.palrecommendationdto.WatchedAnime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Data
@NoArgsConstructor
public class RecommendationOutbox {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID eventId;

    private String aggregateId;

    private Class<?> eventType;

    @CreationTimestamp
    private LocalDateTime addedTime;

    private LocalDateTime sentTime;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode payload;

    public RecommendationOutbox(String aggregateId, Class<? extends WatchedAnime> eventType, JsonNode payload) {
       this.aggregateId = aggregateId;
       this.eventType = eventType;
       this.payload = payload;
    }
}
