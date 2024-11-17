package com.test.task.model.dto;

import com.test.task.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    long id;

    @Size(min = 2, max = 30, message = "The name must contain from 2 to 30 characters")
    String name;

    @Email(message = "The email must match the template")
    String email;

    @NotNull(message = "The password is empty")
    String password;

    @NotNull(message = "User has no role assigned")
    UserRole role;
}
