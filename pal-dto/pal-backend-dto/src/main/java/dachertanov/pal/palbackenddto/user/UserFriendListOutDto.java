package dachertanov.pal.palbackenddto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Schema(description = "DTO для возврата друзей пользователя")
@Data
public class UserFriendListOutDto {
    @Schema(description = "id пользователя", example = "f4df98b1-a51c-4118-b06e-054f11761139")
    @NotNull
    private UUID userId;

    @Schema(description = "Список id друзей пользователя", example = "[f4df98b1-a51c-4118-b06e-054f11761139, f4df98b1-a51c-4118-b06e-054f11761139]")
    @NotNull
    private List<UUID> friendList;
}
