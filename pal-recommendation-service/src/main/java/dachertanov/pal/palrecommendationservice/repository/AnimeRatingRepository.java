package dachertanov.pal.palrecommendationservice.repository;

import dachertanov.pal.palrecommendationservice.entity.AnimeRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AnimeRatingRepository extends JpaRepository<AnimeRating, UUID> {
    @Modifying
    @Query("update AnimeRating animeRating " +
            "set animeRating.weekViews = 0")
    void clearWeekRating();

    @Modifying
    @Query("update AnimeRating animeRating " +
            "set animeRating.monthViews = 0")
    void clearMonthRating();

    @Modifying
    @Query("update AnimeRating animeRating " +
            "set animeRating.yearViews = 0")
    void clearYearRating();
}
