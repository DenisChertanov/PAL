package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.UserAnimeActivity;
import dachertanov.pal.palbackendservice.entity.id.UserAnimeActivityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnimeActivityRepository extends JpaRepository<UserAnimeActivity, UserAnimeActivityId> {
}
