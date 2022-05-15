package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.anime.playlist.AnimePlaylistItem;
import dachertanov.pal.palbackenddto.anime.playlist.AnimePlaylistOutDto;
import dachertanov.pal.palbackendservice.entity.Anime;
import dachertanov.pal.palbackendservice.entity.AnimePlaylist;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class AnimePlaylistMapper {
    private final AnimeMapper animeMapper;

    public AnimePlaylistOutDto makeOutDto(AnimePlaylist animePlaylist, Integer userMark,
                                          List<dachertanov.pal.palbackendservice.entity.AnimePlaylistMapper> mapperList) {
        AnimePlaylistOutDto dto = new AnimePlaylistOutDto();
        dto.setAnimePlaylistId(animePlaylist.getAnimePlaylistId());
        dto.setAuthorUserId(animePlaylist.getUserInfo().getUserId());
        dto.setTitle(animePlaylist.getTitle());
        dto.setDescription(animePlaylist.getDescription());
        dto.setImageUrl(animePlaylist.getImageUrl());
        dto.setGeneralMark(animePlaylist.getMark());

        dto.setUserMark(userMark);

        if (animePlaylist.getAnimeList() != null) {
            Map<UUID, Anime> animeMap = animePlaylist.getAnimeList().stream()
                    .collect(Collectors.toMap(Anime::getAnimeId, Function.identity()));
            dto.setItems(
                    mapperList.stream()
                            .map(mapper -> makeDtoItemFromEntity(mapper, animeMap))
                            .sorted(Comparator.comparingInt(AnimePlaylistItem::getOrder))
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public AnimePlaylistItem makeDtoItemFromEntity(dachertanov.pal.palbackendservice.entity.AnimePlaylistMapper animePlaylistMapper,
                                                   Map<UUID, Anime> animeMap) {
        AnimePlaylistItem item = new AnimePlaylistItem();
        item.setOrder(animePlaylistMapper.getSequence());
        item.setAnime(animeMapper.entityToOut(animeMap.get(animePlaylistMapper.getAnimeId())));

        return item;
    }
}
