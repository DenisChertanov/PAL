package dachertanov.pal.palbackenddto.anime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "DTO для отдачи аниме статистики пользвоателя по аниме")
@Data
public class UserAnimeActivityOutDto {
    @Schema(description = "id пользователя", example = "23be9952-1dbb-4e6e-bb41-a80c212c61ef")
    @NotNull
    private UUID userId;

    @Schema(description = "id аниме", example = "23be9952-1dbb-4e6e-bb41-a80c212c61ef")
    @NotNull
    private UUID animeId;

    @Schema(description = "Оценка аниме от пользователя", example = "7.3", minimum = "0", maximum = "10")
    @NotNull
    @Min(0)
    @Max(10)
    private Double mark = 0.0;

    @NotNull
    private String review = "";

    @Schema(description = "Последний просмотренный эпизод", example = "1", minimum = "0")
    @NotNull
    @Min(0)
    private Integer lastWatchedEpisode = 0;

    private String stringId;
}
