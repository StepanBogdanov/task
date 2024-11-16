package com.test.task.service.impl;

import com.test.task.mapper.CommentMapper;
import com.test.task.mapper.TaskMapper;
import com.test.task.model.CriteriaModel;
import com.test.task.model.dto.CommentDto;
import com.test.task.model.dto.TaskDto;
import com.test.task.model.entity.Task;
import com.test.task.model.enums.TaskStatus;
import com.test.task.repository.TaskRepository;
import com.test.task.repository.TaskSpecification;
import com.test.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CommentMapper commentMapper;

    @Override
    public TaskDto getTask(long taskId) {
        var taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            //todo: выбросить кастомное исключение
        }
        return taskMapper.toTaskDto(taskOptional.get());
    }

    @Override
    public void createTask(TaskDto taskDto) {
        //todo: validation input
        taskDto.setStatus(TaskStatus.PENDING);
        taskRepository.save(taskMapper.toTask(taskDto));
    }

    @Override
    public void deleteTask(long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void updateTask(TaskDto taskDto) {
        //todo: validation input
        taskRepository.save(taskMapper.toTask(taskDto));
    }

    @Override
    public void changeTaskStatus(TaskStatus status, long taskId) {
        var taskDto = getTask(taskId);
        taskDto.setStatus(status);
        updateTask(taskDto);
    }

    @Override
    public void addComment(CommentDto commentDto, long taskId) {
        var taskDto = getTask(taskId);
        taskDto.getComments().add(commentDto);
        updateTask(taskDto);
    }

    @Override
    public List<TaskDto> getTasks(int page, int size, CriteriaModel criteriaModel) {
        var pageRequest = PageRequest.of(page, size);
        Page<Task> tasks;
        if (criteriaModel == null) {
            tasks = taskRepository.findAll(pageRequest);
        } else {
            tasks = taskRepository.findAll(new TaskSpecification(criteriaModel), pageRequest);
        }
        return tasks.get().map(taskMapper::toTaskDto).collect(Collectors.toList());
    }
}
