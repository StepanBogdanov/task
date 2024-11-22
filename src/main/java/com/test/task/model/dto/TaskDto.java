package com.test.task.model.dto;

import com.test.task.model.enums.TaskPriority;
import com.test.task.model.enums.TaskStatus;
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

    @Size(min = 10, message = "Task name shorter than 10 characters")
    String title;

    @Size(min = 30, message = "Task description shorter than 30 characters")
    String description;

    LocalDateTime created = LocalDateTime.now();

    TaskStatus status;

    @NotNull(message = "The task is not assigned a priority")
    TaskPriority priority;

    String authorName;

    String performerName;

    List<CommentDto> comments = new ArrayList<>();
}
