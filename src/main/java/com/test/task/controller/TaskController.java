package com.test.task.controller;

import com.test.task.model.CriteriaModel;
import com.test.task.model.dto.CommentDto;
import com.test.task.model.dto.TaskDto;
import com.test.task.model.enums.TaskStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface TaskController {

    @GetMapping("/{taskId}")
    ResponseEntity<TaskDto> getTask(@PathVariable("taskId") long taskId);

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    void createTask(@Valid @RequestBody TaskDto taskDto);

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteTask(@PathVariable("taskId") long taskId);

    @PatchMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    void updateTask(@Valid @RequestBody TaskDto taskDto);

    @PatchMapping("/{taskId}/status")
    void changeTaskStatus(@RequestParam("status") TaskStatus status, @PathVariable("taskId") long taskId);

    @PostMapping("/{taskId}/comment")
    void addComment(@Valid @RequestBody CommentDto commentDto, @PathVariable("taskId") long taskId);

    @GetMapping("/")
    ResponseEntity<List<TaskDto>> getTasks(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size,
                                           @RequestBody(required = false) CriteriaModel criteriaModel);

    @PatchMapping("/{taskId}/performer")
    @PreAuthorize("hasRole('ADMIN')")
    void assignPerformer(@RequestParam("userName") String userName, @PathVariable("taskId") long taskId);
}
