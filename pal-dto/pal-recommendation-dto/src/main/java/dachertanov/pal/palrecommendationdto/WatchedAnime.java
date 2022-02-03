package dachertanov.pal.palrecommendationdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchedAnime {
    private UUID userId;
    private UUID animeId;
}
