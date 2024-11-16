package com.test.task.controller;

import com.test.task.model.CriteriaModel;
import com.test.task.model.dto.CommentDto;
import com.test.task.model.dto.TaskDto;
import com.test.task.model.enums.TaskStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface TaskController {

    @GetMapping("/{taskId}")
    ResponseEntity<TaskDto> getTask(@PathVariable("taskId") long taskId);

    @PostMapping("/")
    void createTask(@RequestBody TaskDto taskDto);

    @DeleteMapping("/{taskId}")
    void deleteTask(@PathVariable("taskId") long taskId);

    @PatchMapping("/")
    void updateTask(@RequestBody TaskDto taskDto);

    @PatchMapping("/{taskId}/status")
    void changeTaskStatus(@RequestParam("status") TaskStatus status, @PathVariable("taskId") long taskId);

    @PostMapping("/{taskId}/comment")
    void addComment(@RequestBody CommentDto commentDto, @PathVariable("taskId") long taskId);

    @GetMapping("/")
    ResponseEntity<List<TaskDto>> getTasks(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size,
                                           @RequestBody(required = false) CriteriaModel criteriaModel);
}
