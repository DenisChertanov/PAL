package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.AnimeState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AnimeStateRepository extends JpaRepository<AnimeState, UUID> {
    Optional<AnimeState> findByStateEquals(String state);
}
