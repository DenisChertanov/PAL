package dachertanov.pal.palrecommendationservice.service;

import dachertanov.pal.palrecommendationservice.repository.AnimeRatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnimeRatingClearJob {
    private final AnimeRatingRepository animeRatingRepository;

    @Scheduled(cron = "0 0 0 * * MON")
    public void clearWeekAnimeRating() {
        animeRatingRepository.clearWeekRating();
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void clearMonthAnimeRating() {
        animeRatingRepository.clearMonthRating();
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    public void clearYearAnimeRating() {
        animeRatingRepository.clearYearRating();
    }
}
