package dachertanov.pal.palbackenddto.anime.playlist;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class AnimePlaylistOutDto {
    private UUID animePlaylistId;
    private UUID authorUserId;
    private String title;
    private String description;
    private String imageUrl;
    private Integer generalMark;
    private Integer userMark;
    private List<AnimePlaylistItem> items = new ArrayList<>();
}
