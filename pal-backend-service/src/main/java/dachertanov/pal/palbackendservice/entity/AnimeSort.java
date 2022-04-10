package dachertanov.pal.palbackendservice.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
public class AnimeSort {
    @Id
    @Column(name = "anime_sort_id")
    private UUID animeSortId;

    @NotNull
    private String sort;
}
