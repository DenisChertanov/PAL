package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.RecommendationOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecommendationOutboxRepository extends JpaRepository<RecommendationOutbox, UUID> {
    List<RecommendationOutbox> getTop10BySentTimeIsNull();
}
