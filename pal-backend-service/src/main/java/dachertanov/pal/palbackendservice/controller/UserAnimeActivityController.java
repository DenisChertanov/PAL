package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.anime.UserAnimeActivityOutDto;
import dachertanov.pal.palbackenddto.anime.UserAnimeActivityType;
import dachertanov.pal.palbackendservice.service.UserAnimeActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/private/anime-activity")
@AllArgsConstructor
public class UserAnimeActivityController {
    private final UserAnimeActivityService userAnimeActivityService;

    @GetMapping("/{animeId}")
    public ResponseEntity<UserAnimeActivityOutDto> getActivityByAnimeId(@PathVariable UUID animeId) {
        return userAnimeActivityService.getActivityByAnimeId(animeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update-mark/{animeId}")
    public ResponseEntity<UserAnimeActivityOutDto> updateActivityMark(@PathVariable UUID animeId,
                                                                      @RequestBody Double mark) {
        return userAnimeActivityService.updateActivity(animeId, mark, UserAnimeActivityType.MARK)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update-review/{animeId}")
    public ResponseEntity<UserAnimeActivityOutDto> updateActivityReview(@PathVariable UUID animeId,
                                                                      @RequestBody String review) {
        return userAnimeActivityService.updateActivity(animeId, review, UserAnimeActivityType.REVIEW)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/update-last-watched-episode/{animeId}/{lastWatchedEpisode}")
    public ResponseEntity<UserAnimeActivityOutDto> updateActivityLastWatchedEpisode(@PathVariable UUID animeId,
                                                                      @PathVariable Integer lastWatchedEpisode) {
        return userAnimeActivityService.updateActivity(animeId, lastWatchedEpisode, UserAnimeActivityType.LAST_WATCHED_EPISODE)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
