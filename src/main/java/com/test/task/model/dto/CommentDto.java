package com.test.task.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Комментарий к задаче")
public class CommentDto {

    long id;

    @Schema(description = "Автор комментария")
    UserDto author;

    @Schema(description = "Дата создания комментария", format = "yyyy-mm-dd", example = "2024-01-01")
    LocalDateTime created = LocalDateTime.now();

    @Size(min = 10, message = "The comment shorter than 10 characters")
    @Schema(description = "Комментарий", example = "Комментарий к задаче")
    String content;
}
