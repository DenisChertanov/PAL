package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.AnimePlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AnimePlaylistRepository extends JpaRepository<AnimePlaylist, UUID> {
    @Query("select animePlaylist " +
            "from AnimePlaylist animePlaylist " +
            "where animePlaylist.userInfo.userId = :userId")
    List<AnimePlaylist> findAllByUserId(UUID userId);
}
