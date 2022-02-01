package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.anime.UserAnimeActivityOutDto;
import dachertanov.pal.palbackenddto.anime.UserAnimeActivityType;
import dachertanov.pal.palbackendservice.entity.Anime;
import dachertanov.pal.palbackendservice.entity.UserAnimeActivity;
import dachertanov.pal.palbackendservice.entity.UserStatistic;
import dachertanov.pal.palbackendservice.entity.id.UserAnimeActivityId;
import dachertanov.pal.palbackendservice.mapper.UserAnimeActivityMapper;
import dachertanov.pal.palbackendservice.repository.AnimeRepository;
import dachertanov.pal.palbackendservice.repository.UserAnimeActivityRepository;
import dachertanov.pal.palbackendservice.repository.UserStatisticRepository;
import dachertanov.pal.palbackendservice.security.config.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserAnimeActivityService {
    private final AnimeRepository animeRepository;
    private final UserAnimeActivityRepository userAnimeActivityRepository;
    private final UserAnimeActivityMapper userAnimeActivityMapper;
    private final UserStatisticRepository userStatisticRepository;

    public Optional<UserAnimeActivityOutDto> getActivityByAnimeId(UUID animeId) {
        validateAnimeId(animeId);

        UserAnimeActivity entity = getOrCreate(animeId);
        entity = userAnimeActivityRepository.save(entity);

        return Optional.of(userAnimeActivityMapper.entityToOutDto(entity));
    }

    /**
     * Обновляет по переданному типу необходимыую активность (оценка, комментарий, последний просмотренный эпизод)
     */
    public Optional<UserAnimeActivityOutDto> updateActivity(UUID animeId, Object activity, UserAnimeActivityType type) {
        validateAnimeId(animeId);

        UserAnimeActivity entity = getOrCreate(animeId);
        switch (type) {
            case MARK:
                entity.setMark((Double) activity);
                break;
            case REVIEW:
                entity.setReview((String) activity);
                break;
            case LAST_WATCHED_EPISODE:
                setLastWatchedEpisode(animeId, (Integer) activity);
                entity.setLastWatchedEpisode((Integer) activity);
                break;
        }
        entity = userAnimeActivityRepository.save(entity);

        return Optional.of(userAnimeActivityMapper.entityToOutDto(entity));
    }

    /**
     * Обновляет последний просмотренный по аниме эпизод + обновляет статистику пользователя
     */
    private void setLastWatchedEpisode(UUID animeId, Integer lastWatchedEpisode) {
        UUID userId = getCurrentUserId();

        Anime anime = animeRepository.getById(animeId);
        if (lastWatchedEpisode > anime.getEpisodes()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "В аниме с id \"" + animeId + "\" меньше эпизодов");
        }

        if (lastWatchedEpisode.equals(anime.getEpisodes())) { // Пользователь досмотрел аниме
            UserStatistic userStatistic = userStatisticRepository.getById(userId);
            userStatistic.setAnimeSpentHours(userStatistic.getAnimeSpentHours() + anime.getDuration());
            userStatistic.setAnimeCount(userStatistic.getAnimeCount() + 1);
            userStatisticRepository.save(userStatistic);

            //TODO положить сообщение в outbox
        }
    }

    private UserAnimeActivity getOrCreate(UUID animeId) {
        UUID userId = getCurrentUserId();
        return userAnimeActivityRepository.findById(new UserAnimeActivityId(userId, animeId))
                .orElseGet(() -> userAnimeActivityMapper.emptyEntity(userId, animeId));
    }

    private UUID getCurrentUserId() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UUID.fromString(userDetails.getUserId());
    }

    private void validateAnimeId(UUID animeId) {
        if (!animeRepository.existsById(animeId)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Аниме с id \"" + animeId + "\" не найдено");
        }
    }
}
