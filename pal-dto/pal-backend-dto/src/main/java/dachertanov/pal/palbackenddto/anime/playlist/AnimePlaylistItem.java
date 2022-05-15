package dachertanov.pal.palbackenddto.anime.playlist;

import dachertanov.pal.palbackenddto.anime.AnimeOutDto;
import lombok.Data;

@Data
public class AnimePlaylistItem {
    private AnimeOutDto anime;
    private Integer order;
}
