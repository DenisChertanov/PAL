package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.search.FilterObject;
import dachertanov.pal.palbackendservice.service.SearchAnimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/anime-search")
@AllArgsConstructor
public class SearchAnimeController {
    private final SearchAnimeService searchAnimeService;

    @GetMapping("/filter-object")
    public ResponseEntity<FilterObject> getFilterObject() {
        return ResponseEntity.ok(searchAnimeService.getFilterObject());
    }
}
