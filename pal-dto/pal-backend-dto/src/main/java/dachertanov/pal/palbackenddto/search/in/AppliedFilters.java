package dachertanov.pal.palbackenddto.search.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO для передачи на бэк применных фильтров для поиска")
@Data
public class AppliedFilters {
    private Page page;
    private Filter filter;
}
