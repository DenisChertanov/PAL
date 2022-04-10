package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.anime.AnimePageOutDto;
import dachertanov.pal.palbackendservice.entity.Anime;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AnimePageMapper {
    private final AnimeMapper animeMapper;

    public AnimePageOutDto outDtoFromPage(Page<Anime> animePage) {
        AnimePageOutDto dto = new AnimePageOutDto();
        dto.setIsLastPage(animePage.isLast());
        dto.setTotalPages(animePage.getTotalPages());
        dto.setPageNumber(animePage.getNumber());
        dto.setAnimeList(animePage.getContent().stream().map(animeMapper::entityToOut).collect(Collectors.toList()));

        return dto;
    }
}
