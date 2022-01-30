package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.AnimeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AnimeTypeRepository extends JpaRepository<AnimeType, UUID> {
    Optional<AnimeType> findByTypeEquals(String type);
}
