package dachertanov.pal.palbackendservice.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnimeRecommendationId implements Serializable {
    private UUID userId;
    private UUID animeId;
}
