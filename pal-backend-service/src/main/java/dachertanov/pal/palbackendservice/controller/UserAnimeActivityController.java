package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.anime.UserAnimeActivityOutDto;
import dachertanov.pal.palbackenddto.anime.UserAnimeActivityType;
import dachertanov.pal.palbackendservice.service.UserAnimeActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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

    @PostMapping(value = "/update-mark/{animeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserAnimeActivityOutDto> updateActivityMark(@PathVariable UUID animeId,
                                                                      @RequestPart @NotNull String mark) {
        return userAnimeActivityService.updateActivity(animeId, Double.parseDouble(mark), UserAnimeActivityType.MARK)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/update-review/{animeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserAnimeActivityOutDto> updateActivityReview(@PathVariable UUID animeId,
                                                                        @RequestPart @NotNull String review) {
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
