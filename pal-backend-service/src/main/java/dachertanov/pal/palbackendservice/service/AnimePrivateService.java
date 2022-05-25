package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.anime.AnimeInDto;
import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackendservice.entity.*;
import dachertanov.pal.palbackendservice.mapper.AnimeMapper;
import dachertanov.pal.palbackendservice.repository.*;
import dachertanov.pal.palbackendservice.s3.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AnimePrivateService {
    private AnimeStateRepository animeStateRepository;
    private AnimeTagRepository animeTagRepository;
    private AnimeTypeRepository animeTypeRepository;
    private AnimeRepository animeRepository;
    private AnimeMapper animeMapper;
    private final S3Service s3Service;
    private final AnimeRatingRepository animeRatingRepository;

    @Transactional
    public Optional<AnimeOutDto> addAnime(AnimeInDto animeInDto) {
        Anime anime = animeMapper.inDtoToEntity(animeInDto,
                getAnimeStateByState(animeInDto.getStateTitle()),
                getAnimeTypeByType(animeInDto.getTypeTitle()),
                getAnimeTagsByTags(animeInDto.getAnimeTags()));
        anime.setAddedTime(LocalDateTime.now());
        anime = animeRepository.save(anime);

        animeRatingRepository.save(new AnimeRating(anime.getAnimeId(), 0, 0, 0));


        return Optional.of(animeMapper.entityToOut(anime));
    }

    public Optional<AnimeOutDto> updateAnime(AnimeInDto animeInDto, UUID animeId) {
        Optional<Anime> animeOptional = animeRepository.findById(animeId);
        if (animeOptional.isPresent()) {
            Anime updatedAnime = animeMapper.inDtoToEntity(animeInDto,
                    getAnimeStateByState(animeInDto.getStateTitle()),
                    getAnimeTypeByType(animeInDto.getTypeTitle()),
                    getAnimeTagsByTags(animeInDto.getAnimeTags()));
            updatedAnime.setAnimeId(animeId);
            updatedAnime.setImageUrl(animeOptional.get().getImageUrl());
            updatedAnime = animeRepository.save(updatedAnime);

            return Optional.of(animeMapper.entityToOut(updatedAnime));
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Аниме с id \"" + animeId + "\" не найдено");
        }
    }

    public void deleteAnime(UUID animeId) {
        if (animeRepository.existsById(animeId)) {
            animeRepository.deleteById(animeId);
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Аниме с id \"" + animeId + "\" не найдено");
        }
    }

    public String uploadAnimeImage(MultipartFile file, UUID animeId) throws Exception {
        Optional<Anime> animeOptional = animeRepository.findById(animeId);

        if (animeOptional.isPresent()) {
            String imageUrl = s3Service.uploadImage(file, animeId.toString());
            Anime anime = animeOptional.get();
            anime.setImageUrl(imageUrl);
            animeRepository.save(anime);

            return imageUrl;
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Аниме с id \"" + animeId + "\" не найдено");
        }
    }

    private AnimeState getAnimeStateByState(String state) {
        return animeStateRepository.findByStateEquals(state)
                .orElseThrow(() ->
                        new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Статус аниме \"" + state + "\" не найден"));
    }

    private AnimeType getAnimeTypeByType(String type) {
        return animeTypeRepository.findByTypeEquals(type)
                .orElseThrow(() ->
                        new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Тип аниме \"" + type + "\" не найден"));
    }

    private List<AnimeTag> getAnimeTagsByTags(List<String> tags) {
        List<AnimeTag> animeTags = new ArrayList<>();
        tags.forEach(animeTag -> animeTags.add(
                animeTagRepository.findByTagEquals(animeTag)
                        .orElseThrow(() ->
                                new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Аниме тэг \"" + animeTag + "\" не найден"))));

        return animeTags;
    }

}
