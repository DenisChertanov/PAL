package dachertanov.pal.palrecommendationservice.entity;

import dachertanov.pal.palrecommendationservice.entity.id.UserFavouriteGenresId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
@IdClass(UserFavouriteGenresId.class)
@AllArgsConstructor
@NoArgsConstructor
public class UserFavouriteGenres {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "anime_tag_id")
    private UUID animeTagId;

    @NotNull
    private Double recommendationMark = 0.0;
}
