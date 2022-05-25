package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackenddto.user.AnimeTypeDistributionOutDto;
import dachertanov.pal.palbackendservice.entity.Anime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AnimeRepository extends JpaRepository<Anime, UUID> {
    /**
     * Возвращает список аниме, которые подходят по типу, статусу, году
     */
    @Query("select anime from Anime anime " +
            "where (:includeStatesCount = 0 or anime.animeState.animeStateId in :includeStates) and " +
            "(:includeTypesCount = 0 or anime.animeType.animeTypeId in :includeTypes) and " +
            "anime.year >= :yearFrom and " +
            "anime.year <= :yearTo and " +
            "anime.title like :namePrefix")
    public List<Anime> searchByAppliedFilters(List<UUID> includeStates, Integer includeStatesCount,
                                              List<UUID> includeTypes, Integer includeTypesCount,
                                              Integer yearFrom, Integer yearTo, String namePrefix);

    /**
     * Возвращает список аниме, которые содержут ВСЕ переданные жанры (нужно вызвать после searchByAppliedFilters)
     */
    @Query("select anime from Anime anime " +
            "join anime.animeTags tag " +
            "where anime.animeId in :animeIds and " +
            "tag.animeTagId in :includeGenres " +
            "group by anime.animeId " +
            "having count(anime.animeId) = :includeGenresCount")
    public List<Anime> searchByIncludeGenres(List<UUID> animeIds, List<UUID> includeGenres, Long includeGenresCount);

    /**
     * Возвращает список аниме, которые содержут хотя бы один из запрещенных фильтров (такие нужно выкинуть).
     * Нужно вызывать после searchByIncludeGenres
     */
    @Query("select anime from Anime anime " +
            "join anime.animeTags tag " +
            "where anime.animeId in :animeIds and " +
            "tag.animeTagId in :excludeGenres " +
            "group by anime.animeId")
    public List<Anime> searchByExcludeGenres(List<UUID> animeIds, List<UUID> excludeGenres);

    @Query("select anime from Anime anime " +
            "where anime.animeId in :animeIds")
    public Page<Anime> findAllByAnimeIdIn(List<UUID> animeIds, Pageable pageable);

    @Query("select anime.animeId " +
            "from Anime anime " +
            "where anime.animeId in :includeAnimeIds " +
            "and anime.animeId not in :excludeAnimeIds")
    public List<UUID> notWatchedAnimeInIds(List<UUID> includeAnimeIds, List<UUID> excludeAnimeIds);

    @Query("select anime.animeId " +
            "from Anime anime " +
            "inner join UserAnimeActivity userAnimeActivity on anime.animeId = userAnimeActivity.animeId " +
            "where anime.animeId in :animeIds " +
            "and userAnimeActivity.userId in :watchedByUsers " +
            "and anime.episodes = userAnimeActivity.lastWatchedEpisode " +
            "group by anime.animeId " +
            "having count(anime.animeId) = :watchedByUsersSize")
    public List<UUID> findAllByAnimeIdsInAndWatchedByUsers(List<UUID> animeIds, List<UUID> watchedByUsers, Long watchedByUsersSize);

    @Query("select anime from Anime anime " +
            "join UserAnimeRecommendation userAnimeRecommendation on anime.animeId = userAnimeRecommendation.animeId " +
            "where anime.animeId in :animeIds " +
            "and userAnimeRecommendation.userId = :userId")
    public Page<Anime> findAllByAnimeIdInJoinUserRecommendation(List<UUID> animeIds, UUID userId, Pageable pageable);

    @Query("select new dachertanov.pal.palbackenddto.user.AnimeTypeDistributionOutDto(anime.animeType.animeTypeId, anime.animeType.type, count(anime.animeId)) " +
            "from Anime anime " +
            "where anime.animeId in :watchedAnimeIds " +
            "group by anime.animeType.animeTypeId, anime.animeType.type")
    List<AnimeTypeDistributionOutDto> getUserAnimeTypeDistribution(List<UUID> watchedAnimeIds);
}
