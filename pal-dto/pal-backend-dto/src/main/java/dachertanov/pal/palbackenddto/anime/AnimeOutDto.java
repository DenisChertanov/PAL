package dachertanov.pal.palbackenddto.anime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "DTO для аниме при передаче на фронт")
@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class AnimeOutDto {
    @Schema(description = "id аниме", example = "bd12e906-0226-446c-be8b-56e67e9eec0d")
    @NotNull
    private UUID animeId;

    @Schema(description = "Название аниме", example = "Наруто")
    @NotNull
    private String title;

    @Schema(description = "Оценка аниме", example = "7.1", minimum = "0", maximum = "10")
    @NotNull
    @Min(0)
    @Max(10)
    private Double mark = 0.0;

    @Schema(description = "Статус аниме", example = "Вышел")
    @NotNull
    private String stateTitle;

    @Schema(description = "Год выхода", example = "2007")
    @NotNull
    private Integer year;

    @Schema(description = "Студия", example = "Kansai")
    @NotNull
    private String studio;

    @Schema(description = "Режиссёр", example = "Иванов")
    @NotNull
    private String director;

    @Schema(description = "Тип аниме", example = "Сериал")
    @NotNull
    private String typeTitle;

    @Schema(description = "Количество эпизодов", example = "12", minimum = "0")
    @NotNull
    @Min(0)
    private Integer episodes = 0;

    @Schema(description = "Краткое описание", example = "Сюжет разворачивается...")
    private String description;

    @Schema(description = "Продолжительность аниме в часах", example = "4.3", minimum = "0")
    @NotNull
    @Min(0)
    private Double duration = 0.0;

    @Schema(description = "Дата и время добавление аниме в БД")
    private LocalDateTime addedTime;

    @Schema(description = "Список аниме тэгов", example = "[\"Драма\", \"Романтика\"]")
    @NotNull
    private List<String> animeTags;
}
