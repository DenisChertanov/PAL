package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.search.Sort;
import dachertanov.pal.palbackendservice.entity.AnimeSort;
import org.springframework.stereotype.Component;

@Component
public class AnimeSortMapper {
    public Sort dtoFromEntity(AnimeSort animeSort) {
        Sort sort = new Sort();
        sort.setId(animeSort.getAnimeSortId());
        sort.setName(animeSort.getSort());

        return sort;
    }
}
