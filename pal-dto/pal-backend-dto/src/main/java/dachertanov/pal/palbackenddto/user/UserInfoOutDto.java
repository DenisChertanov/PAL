package dachertanov.pal.palbackenddto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "DTO для передачи информации о пользователе на фронт")
@Data
public class UserInfoOutDto {
    @Schema(description = "id пользователя", example = "bd12e906-0226-446c-be8b-56e67e9eec0d")
    @NotNull
    private UUID userId;

    @Schema(description = "Логин пользователя", example = "my_login")
    @NotNull
    private String userName;

    @Schema(description = "Пароль пользователя (зашифрованный)", example = "lLDINGz0YLUUFQuuj5ChAsq0GNM9yHeUAJiL2Be7WUh43Xo3gmXNaw==")
    @NotNull
    private String hashPassword;

    @Schema(description = "Имя пользователя", example = "Иван")
    @NotNull
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    @NotNull
    private String lastName;

    @Schema(description = "Почта пользователя", example = "email@mail.ru")
    @NotNull
    private String email;

    @Schema(description = "Номер телефона пользователя", example = "+71111111111")
    @NotNull
    private String phoneNumber;

    @Schema(description = "Ссылка на аватарку пользователя", example = "http://localhost")
    private String imageUrl;
}
