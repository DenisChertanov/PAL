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

    @GetMapping("/get-by-id/{username}")
    public ResponseEntity<UserStatisticOutDto> getUserStatisticById(@PathVariable String username) {
        return userStatisticService.getUserStatistic(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/last-watched-anime/{username}")
    public List<AnimeOutDto> getLastWatchedAnime(@PathVariable String username) {
        return userStatisticService.getLastWatchedAnime(username, NUMBER_OF_LAST_WATCHED_ANIME);
    }

    @GetMapping("/favourite-genres/{username}")
    public List<UserFavouriteGenresOutDto> getUserFavouriteGenres(@PathVariable String username) {
        return userStatisticService.getUserFavouriteGenres(username);
    }

    @GetMapping("/anime-type-distribution/{username}")
    public List<AnimeTypeDistributionOutDto> getUserAnimeTypeDistribution(@PathVariable String username) {
        return userStatisticService.getUserAnimeTypeDistribution(username);
    }

    @GetMapping("/anime-time-distribution/{username}")
    public List<UserAnimeTimeDistributionOutDto> getUserAnimeTimeDistribution(@PathVariable String username) {
        return userStatisticService.getUserAnimeTimeDistribution(username);
    }
}
