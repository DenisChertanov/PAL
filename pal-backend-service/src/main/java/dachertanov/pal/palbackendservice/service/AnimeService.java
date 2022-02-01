package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackenddto.anime.AnimePageInDto;
import dachertanov.pal.palbackenddto.anime.AnimePageOutDto;
import dachertanov.pal.palbackendservice.entity.Anime;
import dachertanov.pal.palbackendservice.mapper.AnimeMapper;
import dachertanov.pal.palbackendservice.mapper.AnimePageMapper;
import dachertanov.pal.palbackendservice.repository.AnimeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AnimeService {
    private final AnimeMapper animeMapper;
    private final AnimeRepository animeRepository;
    private final AnimePageMapper animePageMapper;

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
}
