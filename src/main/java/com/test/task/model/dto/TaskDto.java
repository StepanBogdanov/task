package com.test.task.model.dto;

import com.test.task.model.enums.TaskPriority;
import com.test.task.model.enums.TaskStatus;
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
public class TaskDto {

    long id;

    @Size(min = 10, max = 50, message = "Название задачи должно содержать от 5 до 50 символов")
    @NotBlank(message = "Название задачи не может быть пустыми")
    String title;

    @Size(min = 30, message = "Описание задачи должно содержать не меньше 30 символов")
    @NotBlank(message = "Описание задачи не может быть пустым")
    String description;

    LocalDateTime created = LocalDateTime.now();

    TaskStatus status;

    @NotNull(message = "Задаче должен быть поставлен приоритет")
    TaskPriority priority;

    UserDto author;

    UserDto performer;

    List<CommentDto> comments = new ArrayList<>();
}
