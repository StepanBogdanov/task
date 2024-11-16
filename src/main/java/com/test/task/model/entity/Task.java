package com.test.task.model.entity;

import com.test.task.model.enums.TaskPriority;
import com.test.task.model.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String title;

    String description;

    LocalDateTime created;

    @Enumerated(EnumType.STRING)
    TaskStatus status;

    @Enumerated(EnumType.STRING)
    TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    User performer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    List<Comment> comments;
}
