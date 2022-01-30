package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.anime.AnimeInDto;
import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackendservice.service.AnimeCRUDService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/api/private/anime")
@AllArgsConstructor
public class AnimeCRUDController {
    private final AnimeCRUDService animeCRUDService;

    @PostMapping(value = "/add")
    public ResponseEntity<AnimeOutDto> addAnime(@RequestBody AnimeInDto animeInDto) {
        return animeCRUDService.addAnime(animeInDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/upload-image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadAnimeImage(@RequestPart @NotNull MultipartFile file,
                                   @RequestPart @NotNull String animeId) throws Exception {
        return animeCRUDService.uploadAnimeImage(file, UUID.fromString(animeId));
    }
}
