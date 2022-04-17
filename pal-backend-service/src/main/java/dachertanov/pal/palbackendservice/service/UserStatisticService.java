package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackenddto.user.UserStatisticOutDto;
import dachertanov.pal.palbackendservice.entity.Anime;
import dachertanov.pal.palbackendservice.mapper.AnimeMapper;
import dachertanov.pal.palbackendservice.mapper.UserStatisticMapper;
import dachertanov.pal.palbackendservice.repository.AnimeRepository;
import dachertanov.pal.palbackendservice.repository.UserAnimeActivityRepository;
import dachertanov.pal.palbackendservice.repository.UserStatisticRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserStatisticService {
    private final UserStatisticMapper userStatisticMapper;
    private final UserStatisticRepository userStatisticRepository;
    private final UserAnimeActivityRepository userAnimeActivityRepository;
    private final AnimeRepository animeRepository;
    private final AnimeMapper animeMapper;

    public Optional<UserStatisticOutDto> getUserStatistic(UUID userId) {
        return userStatisticRepository.findById(userId)
                .map(userStatisticMapper::entityToOutDto);
    }

    public List<AnimeOutDto> getLastWatchedAnime(UUID userId, int animeCount) {
        Page<UUID> animeIds = userAnimeActivityRepository.findAllTopByUserIdEquals(userId,
                PageRequest.of(0, animeCount, Sort.by(Sort.Direction.DESC, "dateTimeWatched")));
        Page<Anime> lastWatchedAnime = animeRepository.findAllByAnimeIdIn(animeIds.getContent(),
                PageRequest.of(0, animeCount));

        return lastWatchedAnime.getContent().stream()
                .map(animeMapper::entityToOut)
                .collect(Collectors.toList());
    }
}
