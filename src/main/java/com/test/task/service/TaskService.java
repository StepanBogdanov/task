package com.test.task.service;

import com.test.task.model.CriteriaModel;
import com.test.task.model.dto.CommentDto;
import com.test.task.model.dto.TaskDto;
import com.test.task.model.enums.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskDto getTask(long taskId);

    void createTask(TaskDto taskDto);

    void deleteTask(long taskId);

    void updateTask(TaskDto taskDto);

    void changeTaskStatus(TaskStatus status,long taskId);

    void addComment(CommentDto commentDto, long taskId);

    List<TaskDto> getTasks(int page, int size, CriteriaModel criteriaModel);
}
