package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.AnimeRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AnimeRatingRepository extends JpaRepository<AnimeRating, UUID> {
    @Query("select animeRating " +
            "from AnimeRating animeRating " +
            "order by animeRating.weekViews desc")
    List<AnimeRating> weekRating();

    @Query("select animeRating " +
            "from AnimeRating animeRating " +
            "order by animeRating.monthViews  desc")
    List<AnimeRating> monthRating();

    @Query("select animeRating " +
            "from AnimeRating animeRating " +
            "order by animeRating.yearViews desc")
    List<AnimeRating> yearRating();
}
