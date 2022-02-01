package dachertanov.pal.palbackenddto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "DTO для отдачи одной записи пользователь - друг")
@Data
public class UserFriendOutDto {
    @Schema(description = "id пользователя", example = "f4df98b1-a51c-4118-b06e-054f11761139")
    @NotNull
    private UUID userId;

    @Schema(description = "id друга", example = "f4df98b1-a51c-4118-b06e-054f11761139")
    @NotNull
    private UUID friendId;
}
