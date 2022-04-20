package dachertanov.pal.palbackenddto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "DTO для отдачи на фронт объекта по поиску пользователей")
@AllArgsConstructor
@Data
public class UserSearchOutDto {
    @Schema(description = "id пользователя", example = "bd12e906-0226-446c-be8b-56e67e9eec0d")
    @NotNull
    private UUID userId;

    @Schema(description = "Логин пользователя", example = "my_login")
    @NotNull
    private String userName;

    @Schema(description = "Имя пользователя", example = "Иван")
    @NotNull
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    @NotNull
    private String lastName;

    @Schema(description = "Ссылка на аватарку пользователя", example = "http://localhost")
    private String imageUrl;

    @Schema(description = "Количество времени, затраченное на просмотр аниме", example = "1", minimum = "0")
    @NotNull
    @Min(0)
    private Double animeSpentHours;

    @Schema(description = "Количество просмотренных аниме", example = "1", minimum = "0")
    @NotNull
    @Min(0)
    private Integer animeCount;
}
