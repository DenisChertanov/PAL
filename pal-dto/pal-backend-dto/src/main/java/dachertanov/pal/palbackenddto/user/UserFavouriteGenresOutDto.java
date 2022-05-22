package dachertanov.pal.palbackenddto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFavouriteGenresOutDto {
    private UUID genreId;
    private String genreTitle;
    private Double genreMark;
    private int order;
}
