package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.anime.playlist.AnimePlaylistOutDto;
import dachertanov.pal.palbackendservice.security.aop.ValidateUserHasPrivilegeForModifyPlaylist;
import dachertanov.pal.palbackendservice.security.aop.ValidateUserHasPrivilegeForUploadPlaylistImage;
import dachertanov.pal.palbackendservice.service.AnimePlaylistService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/anime-playlist")
@AllArgsConstructor
public class AnimePlaylistController {
    private final AnimePlaylistService animePlaylistService;

    @GetMapping("/create-empty")
    private ResponseEntity<AnimePlaylistOutDto> createEmptyPlaylist() {
        return ResponseEntity.ok(animePlaylistService.createEmptyPlaylist());
    }

    @ValidateUserHasPrivilegeForUploadPlaylistImage
    @PostMapping(value = "/upload-image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AnimePlaylistOutDto> uploadAnimeImage(@RequestPart @NotNull String animePlaylistId,
                                                                @RequestPart @NotNull MultipartFile file) throws Exception {
        return ResponseEntity.ok(animePlaylistService.uploadAnimePlaylistImage(file, UUID.fromString(animePlaylistId)));
    }

    @ValidateUserHasPrivilegeForModifyPlaylist
    @PostMapping(value = "/change-title", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<AnimePlaylistOutDto> changeTitle(@RequestParam MultiValueMap<String, String> paramMap) {
        return ResponseEntity.ok(animePlaylistService.changeTitle(paramMap.getFirst("title"),
                UUID.fromString(paramMap.getFirst("animePlaylistId"))));
    }

    @ValidateUserHasPrivilegeForModifyPlaylist
    @PostMapping(value = "/change-description", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<AnimePlaylistOutDto> changeDescription(@RequestParam MultiValueMap<String, String> paramMap) {
        return ResponseEntity.ok(animePlaylistService.changeDescription(paramMap.getFirst("description"),
                UUID.fromString(paramMap.getFirst("animePlaylistId"))));
    }

    @PostMapping(value = "/set-mark", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<AnimePlaylistOutDto> setMark(@RequestParam MultiValueMap<String, String> paramMap) {
        return ResponseEntity.ok(animePlaylistService.setMark(Integer.parseInt(paramMap.getFirst("mark")),
                UUID.fromString(paramMap.getFirst("animePlaylistId"))));
    }

    @GetMapping("/get/{animePlaylistId}")
    public ResponseEntity<AnimePlaylistOutDto> getById(@PathVariable UUID animePlaylistId) {
        return ResponseEntity.ok(animePlaylistService.getById(animePlaylistId));
    }

    @ValidateUserHasPrivilegeForModifyPlaylist
    @PostMapping(value = "/push-back-anime", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<AnimePlaylistOutDto> pushBackAnime(@RequestParam MultiValueMap<String, String> paramMap) {
        return ResponseEntity.ok(animePlaylistService.pushBackAnime(
                UUID.fromString(paramMap.getFirst("animeId")),
                UUID.fromString(paramMap.getFirst("animePlaylistId"))
        ));
    }

    @ValidateUserHasPrivilegeForModifyPlaylist
    @PostMapping(value = "/change-anime-order", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<AnimePlaylistOutDto> changeAnimeOrder(@RequestParam MultiValueMap<String, String> paramMap) {
        return ResponseEntity.ok(animePlaylistService.changeAnimeOrder(
                UUID.fromString(paramMap.getFirst("animeId")),
                UUID.fromString(paramMap.getFirst("animePlaylistId")),
                Integer.parseInt(paramMap.getFirst("order"))
        ));
    }

    @ValidateUserHasPrivilegeForModifyPlaylist
    @PostMapping(value = "/remove-anime", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<AnimePlaylistOutDto> removeAnime(@RequestParam MultiValueMap<String, String> paramMap) {
        return ResponseEntity.ok(animePlaylistService.removeAnime(
                UUID.fromString(paramMap.getFirst("animeId")),
                UUID.fromString(paramMap.getFirst("animePlaylistId"))
        ));
    }

    @PostMapping(value = "/all-by-user", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public List<AnimePlaylistOutDto> allByUser(@RequestParam MultiValueMap<String, String> paramMap) {
        return animePlaylistService.getAllUserPlaylists(UUID.fromString(paramMap.getFirst("userId")));
    }

    @GetMapping(value = "/all-my")
    public List<AnimePlaylistOutDto> allMy() {
        return animePlaylistService.getAllCurrentUserPlaylists();
    }
}
