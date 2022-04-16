package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.search.out.FilterObject;
import dachertanov.pal.palbackendservice.entity.AnimeSort;
import dachertanov.pal.palbackendservice.entity.AnimeState;
import dachertanov.pal.palbackendservice.entity.AnimeTag;
import dachertanov.pal.palbackendservice.entity.AnimeType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SearchAnimeMapper {
    private final AnimeTagMapper animeTagMapper;
    private final AnimeTypeMapper animeTypeMapper;
    private final AnimeStateMapper animeStateMapper;
    private final AnimeSortMapper animeSortMapper;

    public FilterObject filterObjectFromFilters(List<AnimeTag> animeTags, List<AnimeType> animeTypes,
                                                List<AnimeState> animeStates, List<AnimeSort> animeSorts) {
        FilterObject filterObject = new FilterObject();
        filterObject.setGenres(animeTags.stream().map(animeTagMapper::dtoFromEntity).collect(Collectors.toList()));
        filterObject.setTypes(animeTypes.stream().map(animeTypeMapper::dtoFromEntity).collect(Collectors.toList()));
        filterObject.setStates(animeStates.stream().map(animeStateMapper::dtoFromEntity).collect(Collectors.toList()));
        filterObject.setSorts(animeSorts.stream().map(animeSortMapper::dtoFromEntity).collect(Collectors.toList()));

        return filterObject;
    }
}
