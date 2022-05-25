package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackenddto.anime.AnimePageInDto;
import dachertanov.pal.palbackenddto.anime.AnimePageOutDto;
import dachertanov.pal.palbackenddto.anime.rating.AnimeRatingItemDto;
import dachertanov.pal.palbackendservice.entity.Anime;
import dachertanov.pal.palbackendservice.entity.AnimeRating;
import dachertanov.pal.palbackendservice.mapper.AnimeMapper;
import dachertanov.pal.palbackendservice.mapper.AnimePageMapper;
import dachertanov.pal.palbackendservice.repository.AnimeRatingRepository;
import dachertanov.pal.palbackendservice.repository.AnimeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimeService {
    private final static Random random = new Random();

    private final AnimeMapper animeMapper;
    private final AnimeRepository animeRepository;
    private final AnimePageMapper animePageMapper;
    private final AnimeRatingRepository animeRatingRepository;

    public Optional<AnimeOutDto> getAnimeById(UUID animeId) {
        if (animeRepository.existsById(animeId)) {
            return animeRepository.findById(animeId)
                    .map(animeMapper::entityToOut);
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Аниме с id \"" + animeId + "\" не найдено");
        }
    }

    public Optional<AnimePageOutDto> getAnimeListByPage(AnimePageInDto animePageInDto) {
        Page<Anime> animeList = animeRepository.findAll(PageRequest.of(animePageInDto.getPageNumber(), animePageInDto.getPageSize()));

        if (animeList.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Аниме page с AnimePageInDto \"" + animePageInDto + "\" не найден");
        }
        return Optional.of(animePageMapper.outDtoFromPage(animeList));
    }

    public Optional<AnimeOutDto> getRandomAnime() {
        List<Anime> allAnime = animeRepository.findAll();

        if (allAnime.isEmpty()) {
            return Optional.empty();
        }

        int randomIndex = random.nextInt(allAnime.size());
        return Optional.of(animeMapper.entityToOut(allAnime.get(randomIndex)));
    }

    @Transactional
    public List<AnimeRatingItemDto> getAnimeRating(String period) {
        List<AnimeRating> animeRatingList = new ArrayList<>();
        switch (period) {
            case "week":
                animeRatingList = animeRatingRepository.weekRating();
                break;
            case "month":
                animeRatingList = animeRatingRepository.monthRating();
                break;
            case "year":
                animeRatingList = animeRatingRepository.yearRating();
                break;
        }

        List<Anime> animeList = animeRatingList
                .stream()
                .map(animeRating -> animeRepository.findById(animeRating.getAnimeId()).get())
                .collect(Collectors.toList());

        List<AnimeRatingItemDto> result = animeList
                .stream()
                .limit(20)
                .map(animeMapper::entityToRatingDto)
                .collect(Collectors.toList());
        int order = 1;
        for (var animeRatingItemDto : result) {
            animeRatingItemDto.setOrder(order);
            order++;
        }

        return result;
    }
}
