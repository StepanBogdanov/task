package com.test.task.model.dto;

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
public class CommentDto {

    long id;

    String authorName;

    LocalDateTime created = LocalDateTime.now();

    @Size(min = 10, message = "The comment shorter than 10 characters")
    String content;
}
