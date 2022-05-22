package dachertanov.pal.palbackenddto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeTypeDistributionOutDto {
    private UUID animeTypeId;
    private String animeTypeTitle;
    private Long count;
}
