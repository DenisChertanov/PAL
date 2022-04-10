package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.search.FilterObject;
import dachertanov.pal.palbackendservice.entity.AnimeSort;
import dachertanov.pal.palbackendservice.entity.AnimeState;
import dachertanov.pal.palbackendservice.entity.AnimeTag;
import dachertanov.pal.palbackendservice.entity.AnimeType;
import dachertanov.pal.palbackendservice.mapper.SearchAnimeMapper;
import dachertanov.pal.palbackendservice.repository.AnimeSortRepository;
import dachertanov.pal.palbackendservice.repository.AnimeStateRepository;
import dachertanov.pal.palbackendservice.repository.AnimeTagRepository;
import dachertanov.pal.palbackendservice.repository.AnimeTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchAnimeService {
    private final AnimeTagRepository animeTagRepository;
    private final AnimeStateRepository animeStateRepository;
    private final AnimeTypeRepository animeTypeRepository;
    private final AnimeSortRepository animeSortRepository;
    private final SearchAnimeMapper searchAnimeMapper;

    public FilterObject getFilterObject() {
        List<AnimeTag> animeTags = animeTagRepository.findAll();
        List<AnimeState> animeStates = animeStateRepository.findAll();
        List<AnimeType> animeTypes = animeTypeRepository.findAll();
        List<AnimeSort> animeSorts = animeSortRepository.findAll();

        return searchAnimeMapper.filterObjectFromFilters(animeTags, animeTypes, animeStates, animeSorts);
    }
}
