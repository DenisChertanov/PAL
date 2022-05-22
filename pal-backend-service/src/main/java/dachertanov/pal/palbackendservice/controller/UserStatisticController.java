package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackenddto.user.AnimeTypeDistributionOutDto;
import dachertanov.pal.palbackenddto.user.UserAnimeTimeDistributionOutDto;
import dachertanov.pal.palbackenddto.user.UserFavouriteGenresOutDto;
import dachertanov.pal.palbackenddto.user.UserStatisticOutDto;
import dachertanov.pal.palbackendservice.service.UserStatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/statistic")
@AllArgsConstructor
public class UserStatisticController {
    private final static int NUMBER_OF_LAST_WATCHED_ANIME = 20;

    private final UserStatisticService userStatisticService;

    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<UserStatisticOutDto> getUserStatisticById(@PathVariable UUID userId) {
        return userStatisticService.getUserStatistic(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/last-watched-anime/{userId}")
    public List<AnimeOutDto> getLastWatchedAnime(@PathVariable UUID userId) {
        return userStatisticService.getLastWatchedAnime(userId, NUMBER_OF_LAST_WATCHED_ANIME);
    }

    @GetMapping("/favourite-genres/{userId}")
    public List<UserFavouriteGenresOutDto> getUserFavouriteGenres(@PathVariable UUID userId) {
        return userStatisticService.getUserFavouriteGenres(userId);
    }

    @GetMapping("/anime-type-distribution/{userId}")
    public List<AnimeTypeDistributionOutDto> getUserAnimeTypeDistribution(@PathVariable UUID userId) {
        return userStatisticService.getUserAnimeTypeDistribution(userId);
    }

    @GetMapping("/anime-time-distribution/{userId}")
    public List<UserAnimeTimeDistributionOutDto> getUserAnimeTimeDistribution(@PathVariable UUID userId) {
        return userStatisticService.getUserAnimeTimeDistribution(userId);
    }
}
