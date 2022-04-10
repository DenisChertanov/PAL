package dachertanov.pal.palbackenddto.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "DTO для передачи в UI значений всех фильтров")
@Data
public class FilterObject {
    private List<Genre> genres;
    private List<Type> types;
    private List<State> states;
    private List<Sort> sorts;
}
