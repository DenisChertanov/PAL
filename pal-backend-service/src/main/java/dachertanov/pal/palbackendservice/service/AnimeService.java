package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackendservice.mapper.AnimeMapper;
import dachertanov.pal.palbackendservice.repository.AnimeRepository;
import lombok.AllArgsConstructor;
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

    public Optional<AnimeOutDto> getAnimeById(UUID animeId) {
        if (animeRepository.existsById(animeId)) {
            return animeRepository.findById(animeId)
                    .map(animeMapper::entityToOut);
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Аниме с id \"" + animeId + "\" не найдено");
        }
    }
}
