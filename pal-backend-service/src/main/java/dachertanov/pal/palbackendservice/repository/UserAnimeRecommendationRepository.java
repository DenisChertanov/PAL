package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.UserAnimeRecommendation;
import dachertanov.pal.palbackendservice.entity.id.UserAnimeRecommendationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnimeRecommendationRepository extends JpaRepository<UserAnimeRecommendation, UserAnimeRecommendationId> {
}
