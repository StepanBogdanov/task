package com.test.task.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequestDto {

    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    @Schema(description = "Имя пользователя", example = "Ivan Ivanov")
    private String name;

    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    @Schema(description = "Адрес электронной почты", example = "ivan@mail.ru")
    private String email;

    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    @Schema(description = "Пароль", example = "password")
    private String password;
}
