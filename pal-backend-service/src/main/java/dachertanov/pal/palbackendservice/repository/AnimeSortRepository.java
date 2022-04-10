package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.AnimeSort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnimeSortRepository extends JpaRepository<AnimeSort, UUID> {
}
