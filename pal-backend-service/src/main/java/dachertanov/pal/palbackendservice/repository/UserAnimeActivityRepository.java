package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.UserAnimeActivity;
import dachertanov.pal.palbackendservice.entity.id.UserAnimeActivityId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserAnimeActivityRepository extends JpaRepository<UserAnimeActivity, UserAnimeActivityId> {
    @Query("select userAnimeActivity.animeId " +
            "from UserAnimeActivity userAnimeActivity " +
            "where userAnimeActivity.userId = :userId " +
            "and userAnimeActivity.dateTimeWatched is not null")
    Page<UUID> findAllTopByUserIdEquals(UUID userId, Pageable pageable);
}
