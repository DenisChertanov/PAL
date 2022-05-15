package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.AnimePlaylistUserMark;
import dachertanov.pal.palbackendservice.entity.id.AnimePlaylistUserMarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AnimePlaylistUserMarkRepository extends JpaRepository<AnimePlaylistUserMark, AnimePlaylistUserMarkId> {
    @Query("select AVG(animePlaylistUserMark.mark) " +
            "from AnimePlaylistUserMark animePlaylistUserMark " +
            "where animePlaylistUserMark.animePlaylistId = :animePlaylistId " +
            "and animePlaylistUserMark.mark <> 0")
    Double findGeneralMark(UUID animePlaylistId);
}
