package dachertanov.pal.palbackenddto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "DTO для отдачи статистики пользователя")
@Data
public class UserStatisticOutDto {
    @Schema(description = "id пользователя", example = "b58fc3e3-269d-43a6-aebe-a9dfde62442a")
    @NotNull
    private UUID userId;

    @Schema(description = "Количество времени, затраченное на просмотр аниме", example = "1", minimum = "0")
    @NotNull
    @Min(0)
    private Double animeSpentHours;

    @Schema(description = "Количество просмотренных аниме", example = "1", minimum = "0")
    @NotNull
    @Min(0)
    private Integer animeCount;
}
