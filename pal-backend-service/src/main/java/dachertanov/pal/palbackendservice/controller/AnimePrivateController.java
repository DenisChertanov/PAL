package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.anime.AnimeInDto;
import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackendservice.service.AnimePrivateService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/private/anime")
@AllArgsConstructor
public class AnimePrivateController {
    private final AnimePrivateService animePrivateService;

    @PostMapping(value = "/add")
    public ResponseEntity<AnimeOutDto> addAnime(@RequestBody AnimeInDto animeInDto) {
        return animePrivateService.addAnime(animeInDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/update/{animeId}")
    public ResponseEntity<AnimeOutDto> updateAnime(@RequestBody AnimeInDto animeInDto, @PathVariable UUID animeId) {
        return animePrivateService.updateAnime(animeInDto, animeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/delete/{animeId}")
    public void deleteAnime(@PathVariable UUID animeId) {
        animePrivateService.deleteAnime(animeId);
    }

    @PostMapping(value = "/upload-image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadAnimeImage(@RequestPart @NotNull MultipartFile file,
                                   @RequestPart @NotNull String animeId) throws Exception {
        return animePrivateService.uploadAnimeImage(file, UUID.fromString(animeId));
    }
}
