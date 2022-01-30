package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.AnimeTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AnimeTagRepository extends JpaRepository<AnimeTag, UUID> {
    Optional<AnimeTag> findByTagEquals(String tag);
}
