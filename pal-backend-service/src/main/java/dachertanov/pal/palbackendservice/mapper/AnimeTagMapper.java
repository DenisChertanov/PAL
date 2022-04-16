package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.search.out.Genre;
import dachertanov.pal.palbackendservice.entity.AnimeTag;
import org.springframework.stereotype.Component;

@Component
public class AnimeTagMapper {
    public Genre dtoFromEntity(AnimeTag animeTag) {
        Genre genre = new Genre();
        genre.setId(animeTag.getAnimeTagId());
        genre.setName(animeTag.getTag());

        return genre;
    }
}
