package com.test.task.model.dto;

import com.test.task.model.enums.TaskPriority;
import com.test.task.model.enums.TaskStatus;
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

    String title;

    String description;

    LocalDateTime created = LocalDateTime.now();

    TaskStatus status;

    TaskPriority priority;

    UserDto author;

    UserDto performer;

    List<CommentDto> comments = new ArrayList<>();
}
