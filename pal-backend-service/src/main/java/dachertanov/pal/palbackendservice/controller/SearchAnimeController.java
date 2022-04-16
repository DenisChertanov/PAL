package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.anime.AnimePageOutDto;
import dachertanov.pal.palbackenddto.search.in.AppliedFilters;
import dachertanov.pal.palbackenddto.search.out.FilterObject;
import dachertanov.pal.palbackendservice.service.SearchAnimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/anime-search")
@AllArgsConstructor
public class SearchAnimeController {
    private final SearchAnimeService searchAnimeService;

    @GetMapping("/filter-object")
    public ResponseEntity<FilterObject> getFilterObject() {
        return ResponseEntity.ok(searchAnimeService.getFilterObject());
    }

    @PostMapping("/search")
    public ResponseEntity<AnimePageOutDto> searchByAppliedFilters(@RequestBody AppliedFilters appliedFilters) {
        return searchAnimeService.searchByAppliedFilters(appliedFilters)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
