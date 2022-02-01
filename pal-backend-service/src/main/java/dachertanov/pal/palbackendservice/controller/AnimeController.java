package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackendservice.service.AnimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
