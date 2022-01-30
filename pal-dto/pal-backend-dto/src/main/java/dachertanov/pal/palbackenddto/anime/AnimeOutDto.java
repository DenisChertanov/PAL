package dachertanov.pal.palbackenddto.anime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "DTO для аниме при передаче на фронт")
@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class AnimeOutDto {
    @Schema(description = "id аниме", example = "bd12e906-0226-446c-be8b-56e67e9eec0d")
    @NotNull
    private UUID animeId;
}
