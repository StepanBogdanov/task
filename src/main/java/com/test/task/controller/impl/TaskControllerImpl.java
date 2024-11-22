package com.test.task.controller.impl;

import com.test.task.controller.TaskController;
import com.test.task.model.CriteriaModel;
import com.test.task.model.dto.CommentDto;
import com.test.task.model.dto.TaskDto;
import com.test.task.model.enums.TaskStatus;
import com.test.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskDto> getTask(long taskId) {
        return ResponseEntity.ok(taskService.getTask(taskId));
    }

    @Override
    public void createTask(TaskDto taskDto) {
        taskService.createTask(taskDto);
    }

    @Override
    public void deleteTask(long taskId) {
        taskService.deleteTask(taskId);
    }

    @Override
    public void updateTask(TaskDto taskDto) {
        taskService.updateTask(taskDto);
    }

    @Override
    public void changeTaskStatus(TaskStatus status, long taskId) {
        taskService.changeTaskStatus(status, taskId);
    }

    @Override
    public void addComment(CommentDto commentDto, long taskId) {
        taskService.addComment(commentDto, taskId);
    }

    @Override
    public ResponseEntity<List<TaskDto>> getTasks(int page, int size, CriteriaModel criteriaModel) {
        return ResponseEntity.ok(taskService.getTasks(page, size, criteriaModel));
    }
}
