package dachertanov.pal.palbackenddto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "DTO для передачи информации о пользователе на бэк")
@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class UserInfoInDto {
    @Schema(description = "Логин пользователя", example = "my_login")
    @NotNull
    private String username;

    @Schema(description = "Пароль пользователя (не зашифрованный)", example = "my_password")
    @NotNull
    private String password;

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
}
