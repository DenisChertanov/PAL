package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackenddto.anime.AnimePageInDto;
import dachertanov.pal.palbackenddto.anime.AnimePageOutDto;
import dachertanov.pal.palbackenddto.anime.rating.AnimeRatingItemDto;
import dachertanov.pal.palbackendservice.service.AnimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/anime")
@AllArgsConstructor
public class AnimeController {
    private final AnimeService animeService;

    @GetMapping("/get-by-id/{animeId}")
    public ResponseEntity<AnimeOutDto> getAnimeById(@PathVariable UUID animeId) {
        return animeService.getAnimeById(animeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/get-by-page")
    public ResponseEntity<AnimePageOutDto> getAnimeListByPage(@RequestBody AnimePageInDto animePageInDto) {
        return animeService.getAnimeListByPage(animePageInDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-random")
    public ResponseEntity<AnimeOutDto> getRandomAnime() {
        return animeService.getRandomAnime()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/rating/{period}")
    public List<AnimeRatingItemDto> getAnimeRating(@PathVariable String period) {
        return animeService.getAnimeRating(period);
    }
}
