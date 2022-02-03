package dachertanov.pal.palrecommendationservice.repository;

import dachertanov.pal.palrecommendationservice.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnimeRepository extends JpaRepository<Anime, UUID> {
}
