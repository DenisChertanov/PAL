package dachertanov.pal.palrecommendationservice.service;

import dachertanov.pal.palrecommendationdto.WatchedAnime;
import dachertanov.pal.palrecommendationservice.entity.Anime;
import dachertanov.pal.palrecommendationservice.entity.AnimeTagCntResponse;
import dachertanov.pal.palrecommendationservice.entity.UserAnimeRecommendation;
import dachertanov.pal.palrecommendationservice.repository.AnimeRepository;
import dachertanov.pal.palrecommendationservice.repository.UserAnimeRecommendationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class RecommendationService {
    private final UserAnimeRecommendationRepository userAnimeRecommendationRepository;
    private final AnimeRepository animeRepository;

    @Transactional
    public void updateRecommendation(WatchedAnime watchedAnime) {
        Map<UUID, AnimeTagCntResponse> tagsPopularity =
                userAnimeRecommendationRepository.getUserAnimeTagsMarks(watchedAnime.getUserId())
                        .stream()
                        .collect(Collectors.toMap(AnimeTagCntResponse::getAnimeTagId, Function.identity()));
        log.info("Tags popularity: {}", tagsPopularity);

        List<Anime> animeList = animeRepository.findAll();
        for (var anime : animeList) {
            double recommendationMark = 0;
            for (var tag : anime.getAnimeTags()) {
                if (tagsPopularity.containsKey(tag.getAnimeTagId())) {
                    recommendationMark += tagsPopularity.get(tag.getAnimeTagId()).getCnt();
                }
            }

            userAnimeRecommendationRepository.save(
                    new UserAnimeRecommendation(watchedAnime.getUserId(), anime.getAnimeId(), recommendationMark));
        }
    }
}
