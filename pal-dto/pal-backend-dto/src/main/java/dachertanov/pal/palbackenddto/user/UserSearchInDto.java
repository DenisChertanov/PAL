package dachertanov.pal.palbackenddto.user;

import dachertanov.pal.palbackenddto.search.in.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO для передачи на бэк объекта для поиска пользователей")
@Data
public class UserSearchInDto {
    private Page page;
    private String userPrefix;
}
