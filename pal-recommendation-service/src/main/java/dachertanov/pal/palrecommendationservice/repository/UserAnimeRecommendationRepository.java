package dachertanov.pal.palrecommendationservice.repository;

import dachertanov.pal.palrecommendationservice.entity.AnimeTagCntResponse;
import dachertanov.pal.palrecommendationservice.entity.UserAnimeRecommendation;
import dachertanov.pal.palrecommendationservice.entity.id.UserAnimeRecommendationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserAnimeRecommendationRepository extends JpaRepository<UserAnimeRecommendation, UserAnimeRecommendationId> {
    @Query(value = "select" +
            " new dachertanov.pal.palrecommendationservice.entity.AnimeTagCntResponse(mapper.animeTagId, count(activity)) " +
            "from UserAnimeActivity activity " +
            "inner join Anime _anime on activity.animeId = _anime.animeId " +
            "inner join AnimeTagMapper mapper on _anime.animeId = mapper.animeId " +
            "where activity.lastWatchedEpisode = _anime.episodes " +
            "and activity.userId = :userId " +
            "group by mapper.animeTagId ")
    List<AnimeTagCntResponse> getUserAnimeTagsMarks(UUID userId);
}
