package com.test.task.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    User author;

    @Column(name = "created", updatable = false)
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    LocalDateTime created;

    @Column(name = "content")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    String content;
}
