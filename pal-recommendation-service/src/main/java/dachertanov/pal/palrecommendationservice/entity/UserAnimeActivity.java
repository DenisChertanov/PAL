package dachertanov.pal.palrecommendationservice.entity;

import dachertanov.pal.palrecommendationservice.entity.id.UserAnimeActivityId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@IdClass(UserAnimeActivityId.class)
public class UserAnimeActivity implements Serializable {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "anime_id")
    private UUID animeId;

    @NotNull
    @Min(0)
    @Max(10)
    private Double mark = 0.0;

    private String review;

    @NotNull
    private Integer lastWatchedEpisode = 0;
}
