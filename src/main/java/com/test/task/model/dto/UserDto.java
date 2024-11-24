package com.test.task.model.dto;

import com.test.task.model.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Пользователь")
public class UserDto {

    long id;

    @Schema(description = "Имя пользователя", example = "Ivan Ivanov")
    String name;

    @Schema(description = "Адрес электронной почты", example = "ivan@mail.ru")
    String email;

    @Schema(description = "Пароль", example = "password")
    String password;

    @Schema(description = "Роль пользователя", example = "ROLE_ADMIN")
    UserRole role;
}
