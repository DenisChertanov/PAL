package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.anime.UserAnimeActivityOutDto;
import dachertanov.pal.palbackendservice.entity.UserAnimeActivity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserAnimeActivityMapper {
    public UserAnimeActivityOutDto entityToOutDto(UserAnimeActivity entity) {
        UserAnimeActivityOutDto dto = new UserAnimeActivityOutDto();
        dto.setUserId(entity.getUserId());
        dto.setAnimeId(entity.getAnimeId());
        dto.setMark(entity.getMark());
        dto.setReview(entity.getReview() == null ? "" : entity.getReview());
        dto.setLastWatchedEpisode(entity.getLastWatchedEpisode());

        return dto;
    }

    public UserAnimeActivity emptyEntity(UUID userId, UUID animeId) {
        UserAnimeActivity entity = new UserAnimeActivity();
        entity.setUserId(userId);
        entity.setAnimeId(animeId);

        return entity;
    }
}
