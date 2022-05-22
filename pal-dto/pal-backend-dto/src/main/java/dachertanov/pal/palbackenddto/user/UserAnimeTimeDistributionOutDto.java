package dachertanov.pal.palbackenddto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnimeTimeDistributionOutDto {
    private String period;
    private Long count;
}
