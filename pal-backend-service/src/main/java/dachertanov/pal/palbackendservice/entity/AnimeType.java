package dachertanov.pal.palbackendservice.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
public class AnimeType {
    @Id
    @Column(name = "anime_type_id")
    private UUID animeTypeId;

    @NotNull
    private String type;
}
