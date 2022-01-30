package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnimeRepository extends JpaRepository<Anime, UUID> {
}
