package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.anime.AnimeInDto;
import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import dachertanov.pal.palbackendservice.entity.Anime;
import dachertanov.pal.palbackendservice.entity.AnimeState;
import dachertanov.pal.palbackendservice.entity.AnimeTag;
import dachertanov.pal.palbackendservice.entity.AnimeType;
import dachertanov.pal.palbackendservice.mapper.AnimeMapper;
import dachertanov.pal.palbackendservice.repository.AnimeRepository;
import dachertanov.pal.palbackendservice.repository.AnimeStateRepository;
import dachertanov.pal.palbackendservice.repository.AnimeTagRepository;
import dachertanov.pal.palbackendservice.repository.AnimeTypeRepository;
import dachertanov.pal.palbackendservice.s3.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

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

    public Optional<AnimeOutDto> addAnime(AnimeInDto animeInDto) {
        Anime anime = animeMapper.inDtoToEntity(animeInDto,
                getAnimeStateByState(animeInDto.getStateTitle()),
                getAnimeTypeByType(animeInDto.getTypeTitle()),
                getAnimeTagsByTags(animeInDto.getAnimeTags()));
        anime = animeRepository.save(anime);

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
