package dachertanov.pal.palbackendservice.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
public class AnimeState {
    @Id
    @Column(name = "anime_state_id")
    private UUID animeStateId;

    @NotNull
    private String state;
}
