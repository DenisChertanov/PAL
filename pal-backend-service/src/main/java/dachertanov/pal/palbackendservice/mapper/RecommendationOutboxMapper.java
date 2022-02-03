package dachertanov.pal.palbackendservice.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dachertanov.pal.palbackendservice.entity.RecommendationOutbox;
import dachertanov.pal.palrecommendationdto.WatchedAnime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecommendationOutboxMapper {
    private final ObjectMapper objectMapper;

    public RecommendationOutbox map(WatchedAnime watchedAnime) {
        return new RecommendationOutbox(watchedAnime.getUserId().toString(),
                watchedAnime.getClass(), convertToJsonNode(watchedAnime));
    }

    private JsonNode convertToJsonNode(Object event) {
        return objectMapper.convertValue(event, JsonNode.class);
    }
}
