package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackendservice.mapper.RecommendationOutboxMapper;
import dachertanov.pal.palbackendservice.repository.RecommendationOutboxRepository;
import dachertanov.pal.palrecommendationdto.WatchedAnime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OutboxService {
    private final RecommendationOutboxRepository recommendationOutboxRepository;
    private final RecommendationOutboxMapper recommendationOutboxMapper;

    @Transactional
    public void saveEvent(UUID userId, UUID animeId) {
        recommendationOutboxRepository.save(recommendationOutboxMapper.map(new WatchedAnime(userId, animeId)));
    }
}
