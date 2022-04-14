package dachertanov.pal.palbackenddto.anime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "DTO для возврата аниме page")
@Data
public class AnimePageOutDto {
    @Schema(description = "Порядковый номер страницы аниме", example = "0", minimum = "0")
    @NotNull
    @Min(0)
    private Integer pageNumber;

    private Integer totalPages;

    @Schema(description = "Показатель является ли переданная аниме страница последней", example = "false")
    @NotNull
    private Boolean isLastPage = false;

    @Schema(description = "Аниме карточки текущей страницы")
    @NotNull
    private List<AnimeOutDto> animeList;
}
