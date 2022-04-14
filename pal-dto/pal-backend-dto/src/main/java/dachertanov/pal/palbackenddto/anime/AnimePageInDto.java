package dachertanov.pal.palbackenddto.anime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(description = "DTO для передачи информации какой page аниме нужно вернуть")
@Data
public class AnimePageInDto {
    @Schema(description = "Порядковый номер страницы", example = "0", minimum = "0")
    @NotNull
    @Min(0)
    private Integer pageNumber;

    @Schema(description = "Количество аниме на одной странице", example = "2", minimum = "1")
    @NotNull
    @Min(1)
    private Integer pageSize;
}
