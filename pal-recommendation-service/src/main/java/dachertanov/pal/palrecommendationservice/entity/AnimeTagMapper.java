package dachertanov.pal.palrecommendationservice.entity;

import dachertanov.pal.palrecommendationservice.entity.id.AnimeTagMapperId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.UUID;

@Entity
@Data
@IdClass(AnimeTagMapperId.class)
public class AnimeTagMapper {
    @Id
    @Column(name = "anime_id")
    private UUID animeId;

    @Id
    @Column(name = "anime_tag_id")
    private UUID animeTagId;
}
