package dachertanov.pal.palbackenddto.user.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRatingListOutDto {
    private List<UserRating> userRatingList;
    private String period;
}
