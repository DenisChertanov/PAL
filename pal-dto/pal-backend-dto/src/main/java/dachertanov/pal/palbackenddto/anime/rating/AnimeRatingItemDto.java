package dachertanov.pal.palbackenddto.anime.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeRatingItemDto {
    private UUID animeId;
    private String imageUrl;
    private String title;
    private Double mark;
    private String type;
    private Integer episodes;
    private String state;
    private Integer year;
    private Integer order;
}
