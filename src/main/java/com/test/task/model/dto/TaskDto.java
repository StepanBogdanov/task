package com.test.task.model.dto;

import com.test.task.model.enums.TaskPriority;
import com.test.task.model.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Задача")
public class TaskDto {

    long id;

    @Size(min = 10, max = 50, message = "Название задачи должно содержать от 5 до 50 символов")
    @NotBlank(message = "Название задачи не может быть пустыми")
    @Schema(description = "Название задачи", example = "Протестировать приложение")
    String title;

    @Size(min = 30, message = "Описание задачи должно содержать не меньше 30 символов")
    @NotBlank(message = "Описание задачи не может быть пустым")
    @Schema(description = "Описание задачи", example = "Протестировать приложение с использованием библиотеки JUnit")
    String description;

    @Schema(description = "Дата создания задачи", format = "yyyy-mm-dd", example = "2024-01-01")
    LocalDateTime created = LocalDateTime.now();

    @Schema(description = "Статус задачи", example = "IN_PROGRESS")
    TaskStatus status;

    @NotNull(message = "Задаче должен быть поставлен приоритет")
    @Schema(description = "Приоритет задачи", example = "LOW")
    TaskPriority priority;

    @Schema(description = "Автор задачи")
    UserDto author;

    @Schema(description = "Исполнитель задачи")
    UserDto performer;

    @Schema(description = "Список комментариев")
    List<CommentDto> comments = new ArrayList<>();
}
