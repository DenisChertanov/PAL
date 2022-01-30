package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.anime.AnimeInDto;
import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackendservice.entity.Anime;
import dachertanov.pal.palbackendservice.entity.AnimeState;
import dachertanov.pal.palbackendservice.entity.AnimeTag;
import dachertanov.pal.palbackendservice.entity.AnimeType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnimeMapper {
    public Anime inDtoToEntity(AnimeInDto animeInDto, AnimeState animeState, AnimeType animeType, List<AnimeTag> animeTags) {
        Anime anime = new Anime();
        anime.setTitle(animeInDto.getTitle());
        anime.setMark(animeInDto.getMark());
        anime.setYear(animeInDto.getYear());
        anime.setStudio(animeInDto.getStudio());
        anime.setDirector(animeInDto.getDirector());
        anime.setEpisodes(animeInDto.getEpisodes());
        anime.setDescription(animeInDto.getDescription());
        anime.setAnimeState(animeState);
        anime.setAnimeType(animeType);
        anime.setAnimeTags(animeTags);

        return anime;
    }

    public AnimeOutDto entityToOut(Anime anime) {
        AnimeOutDto animeOutDto = new AnimeOutDto();
        animeOutDto.setAnimeId(anime.getAnimeId());

        return animeOutDto;
    }
}
