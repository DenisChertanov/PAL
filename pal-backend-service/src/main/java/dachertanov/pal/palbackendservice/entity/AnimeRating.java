package dachertanov.pal.palbackendservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeRating {
    @Id
    @Column(name = "anime_id")
    private UUID animeId;

    @NotNull
    private Integer weekViews = 0;

    @NotNull
    private Integer monthViews = 0;

    @NotNull
    private Integer yearViews = 0;

    public AnimeRating(Integer weekViews, Integer monthViews, Integer yearViews) {
        this.weekViews = weekViews;
        this.monthViews = monthViews;
        this.yearViews = yearViews;
    }
}
