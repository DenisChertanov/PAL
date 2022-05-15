package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.AnimePlaylistMapper;
import dachertanov.pal.palbackendservice.entity.id.AnimePlaylistMapperId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AnimePlaylistMapperRepository extends JpaRepository<AnimePlaylistMapper, AnimePlaylistMapperId> {
    List<AnimePlaylistMapper> findAllByAnimePlaylistIdEquals(UUID animePlaylistId);

    @Query("select MAX(animePlaylistMapper.sequence)" +
            "from AnimePlaylistMapper animePlaylistMapper " +
            "where animePlaylistMapper.animePlaylistId = :animePlaylistId")
    Integer findMaxSequence(UUID animePlaylistId);
}
