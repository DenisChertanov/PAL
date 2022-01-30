package dachertanov.pal.palbackendservice.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
@Embeddable
public class AnimeTag {
    @Id
    @Column(name = "anime_tag_id")
    private UUID animeTagId;

    @NotNull
    private String tag;
}
