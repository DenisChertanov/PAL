package dachertanov.pal.palrecommendationservice.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeTagMapperId implements Serializable {
    private UUID animeId;
    private UUID animeTagId;
}
