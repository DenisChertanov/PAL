package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.anime.AnimeInDto;
import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackendservice.entity.Anime;
import dachertanov.pal.palbackendservice.entity.AnimeState;
import dachertanov.pal.palbackendservice.entity.AnimeTag;
import dachertanov.pal.palbackendservice.entity.AnimeType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        anime.setDuration(animeInDto.getDuration());
        anime.setAnimeState(animeState);
        anime.setAnimeType(animeType);
        anime.setAnimeTags(animeTags);
        anime.setSeason(animeInDto.getSeason());
        anime.setAgeRating(animeInDto.getAgeRating());
        anime.setEpisodeDuration(animeInDto.getEpisodeDuration());
        anime.setVoice(animeInDto.getVoice());

        return anime;
    }

    public AnimeOutDto entityToOut(Anime anime) {
        AnimeOutDto animeOutDto = new AnimeOutDto();
        animeOutDto.setAnimeId(anime.getAnimeId());
        animeOutDto.setImageUrl(anime.getImageUrl());
        animeOutDto.setTitle(anime.getTitle());
        animeOutDto.setMark(anime.getMark());
        animeOutDto.setYear(anime.getYear());
        animeOutDto.setStudio(anime.getStudio());
        animeOutDto.setDirector(anime.getDirector());
        animeOutDto.setEpisodes(anime.getEpisodes());
        animeOutDto.setDescription(anime.getDescription());
        animeOutDto.setDuration(anime.getDuration());
        animeOutDto.setAddedTime(anime.getAddedTime());
        animeOutDto.setStateTitle(anime.getAnimeState().getState());
        animeOutDto.setTypeTitle(anime.getAnimeType().getType());
        animeOutDto.setAnimeTags(anime.getAnimeTags().stream().map(AnimeTag::getTag).collect(Collectors.toList()));
        animeOutDto.setSeason(anime.getSeason());
        animeOutDto.setAgeRating(anime.getAgeRating());
        animeOutDto.setEpisodeDuration(anime.getEpisodeDuration());
        animeOutDto.setVoice(anime.getVoice());

        return animeOutDto;
    }
}
