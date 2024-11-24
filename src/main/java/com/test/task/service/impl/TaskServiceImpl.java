package com.test.task.service.impl;

import com.test.task.exception.RequestException;
import com.test.task.mapper.CommentMapper;
import com.test.task.mapper.TaskMapper;
import com.test.task.mapper.UserMapper;
import com.test.task.model.CriteriaModel;
import com.test.task.model.dto.CommentDto;
import com.test.task.model.dto.TaskDto;
import com.test.task.model.entity.Task;
import com.test.task.model.enums.TaskStatus;
import com.test.task.model.enums.UserRole;
import com.test.task.repository.CommentRepository;
import com.test.task.repository.TaskRepository;
import com.test.task.repository.TaskSpecification;
import com.test.task.repository.UserRepository;
import com.test.task.service.TaskService;
import com.test.task.service.UserService;
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
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @Override
    public TaskDto getTask(long taskId) {
        var taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new RequestException(String.format("Задача с Id:%s не найдена", taskId));
        }
        return taskMapper.toTaskDto(taskOptional.get());
    }

    @Override
    public void createTask(TaskDto taskDto) {
        if (taskRepository.existsByTitle(taskDto.getTitle())) {
            throw new RequestException(String.format("Задача с названием %s уже существует", taskDto.getTitle()));
        }
        taskDto.setStatus(TaskStatus.PENDING);
        taskDto.setAuthor(userMapper.toUserDto(userService.getCurrentUser()));
        taskRepository.save(taskMapper.toTask(taskDto));
    }

    @Override
    public void deleteTask(long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void updateTask(TaskDto taskDto) {
        taskRepository.save(taskMapper.toTask(taskDto));
    }

    @Override
    public void changeTaskStatus(TaskStatus status, long taskId) {
        var taskDto = getTask(taskId);
        if (userService.getCurrentUser().getName().equals(taskDto.getPerformer().getName()) ||
                userService.getCurrentUser().getRole() == UserRole.ROLE_ADMIN) {
            taskDto.setStatus(status);
            updateTask(taskDto);
        } else {
            throw new RequestException("У вас недостаточно прав для этого действия");
        }
    }

    @Override
    public void addComment(CommentDto commentDto, long taskId) {
        var taskDto = getTask(taskId);
        commentDto.setAuthor(userMapper.toUserDto(userService.getCurrentUser()));
        var comment = commentMapper.toComment(commentDto);
        commentRepository.save(comment);
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

    @Override
    public void assignPerformer(String userName, long taskId) {
        var performer = userService.getUserByName(userName);
        var taskDto = getTask(taskId);
        taskDto.setPerformer(userMapper.toUserDto(performer));
        taskDto.setStatus(TaskStatus.IN_PROGRESS);
        updateTask(taskDto);
    }
}
