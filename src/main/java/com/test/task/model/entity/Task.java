package com.test.task.model.entity;

import com.test.task.model.enums.TaskPriority;
import com.test.task.model.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.SQLType;
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

    @Column(unique = true, nullable = false, name = "title", length = 50)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    String title;

    @Column( nullable = false, name = "description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    String description;

    @Column(name = "created", updatable = false)
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    LocalDateTime created;

    @Column(name = "status", length = 30)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    TaskStatus status;

    @Column(nullable = false, name = "priority", length = 30)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    User author;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    @JdbcTypeCode(SqlTypes.BIGINT)
    User performer;

    @OneToMany(cascade = CascadeType.ALL)
    List<Comment> comments;

}
