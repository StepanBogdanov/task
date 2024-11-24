package com.test.task.service.impl;

import com.test.task.exception.RequestException;
import com.test.task.mapper.TaskMapper;
import com.test.task.mapper.UserMapper;
import com.test.task.model.dto.TaskDto;
import com.test.task.model.dto.UserDto;
import com.test.task.model.entity.Task;
import com.test.task.model.entity.User;
import com.test.task.model.enums.TaskStatus;
import com.test.task.model.enums.UserRole;
import com.test.task.repository.TaskRepository;
import com.test.task.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    TaskMapper taskMapper;
    @Mock
    UserMapper userMapper;
    @Mock
    TaskRepository taskRepository;
    @Mock
    UserService userService;
    @InjectMocks
    TaskServiceImpl taskService;

    @Test
    public void whenGetTaskAndReturnTask() {
        long taskId = 1L;
        var task = new Task();
        var taskDto = new TaskDto();

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        Mockito.when(taskMapper.toTaskDto(task)).thenReturn(taskDto);

        var result = taskService.getTask(taskId);
        Mockito.verify(taskRepository, times(1)).findById(taskId);
        Mockito.verify(taskMapper, times(1)).toTaskDto(task);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(taskDto);
    }

    @Test
    public void whenGetTaskAndNotReturnTask() {
        long taskId = 1L;

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        var exception = assertThrows(RequestException.class, () -> taskService.getTask(taskId));
        Mockito.verify(taskRepository, times(1)).findById(taskId);
        Mockito.verify(taskMapper, never()).toTaskDto(any());
        assertThat(String.format("Задача с Id:%s не найдена", taskId)).isEqualTo(exception.getMessage());
    }

    @Test
    public void whenCreateTask() {
        var title = "Task title";
        var taskDto = new TaskDto();
        taskDto.setTitle(title);

        Mockito.when(taskRepository.existsByTitle(title)).thenReturn(false);
        Mockito.when(userService.getCurrentUser()).thenReturn(new User());
        Mockito.when(userMapper.toUserDto(any())).thenReturn(new UserDto());
        Mockito.when(taskMapper.toTask(taskDto)).thenReturn(new Task());

        taskService.createTask(taskDto);

        Mockito.verify(taskRepository, times(1)).existsByTitle(title);
        Mockito.verify(userService, times(1)).getCurrentUser();
        Mockito.verify(userMapper, times(1)).toUserDto(any());
        Mockito.verify(taskMapper, times(1)).toTask(taskDto);
        Mockito.verify(taskRepository, times(1)).save(any());
        assertThat(TaskStatus.PENDING).isEqualTo(taskDto.getStatus());
    }

    @Test
    public void whenNotCreateTask() {
        var title = "Task title";
        var taskDto = new TaskDto();
        taskDto.setTitle(title);

        Mockito.when(taskRepository.existsByTitle(title)).thenReturn(true);

        var exception = assertThrows(RequestException.class, () -> taskService.createTask(taskDto));

        Mockito.verify(taskRepository, times(1)).existsByTitle(title);
        Mockito.verify(userService, times(0)).getCurrentUser();
        Mockito.verify(userMapper, times(0)).toUserDto(any());
        Mockito.verify(taskMapper, times(0)).toTask(taskDto);
        Mockito.verify(taskRepository, times(0)).save(any());
        assertThat(String.format("Задача с названием %s уже существует", title)).isEqualTo(exception.getMessage());
    }

    @Test
    public void changeStatusWithTaskPerformer() {
        var performer = new UserDto();
        performer.setName("performer");
        var currentUser = new User();
        currentUser.setName("performer");
        var taskDto = new TaskDto();
        taskDto.setPerformer(performer);

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task()));
        Mockito.when(taskMapper.toTaskDto(any())).thenReturn(taskDto);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        taskService.changeTaskStatus(TaskStatus.COMPLETED, 1L);

        Mockito.verify(taskRepository, times(1)).findById(1L);
        Mockito.verify(taskMapper, times(1)).toTaskDto(any());
        Mockito.verify(userService, times(1)).getCurrentUser();
        Mockito.verify(taskRepository, times(1)).save(any());
        assertThat(TaskStatus.COMPLETED).isEqualTo(taskDto.getStatus());
    }

    @Test
    public void changeStatusWithAdmin() {
        var currentUser = new User();
        currentUser.setRole(UserRole.ROLE_ADMIN);
        currentUser.setName("user");
        var taskDto = new TaskDto();
        taskDto.setPerformer(new UserDto());

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task()));
        Mockito.when(taskMapper.toTaskDto(any())).thenReturn(taskDto);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        taskService.changeTaskStatus(TaskStatus.COMPLETED, 1L);

        Mockito.verify(taskRepository, times(1)).findById(1L);
        Mockito.verify(taskMapper, times(1)).toTaskDto(any());
        Mockito.verify(userService, times(2)).getCurrentUser();
        Mockito.verify(taskRepository, times(1)).save(any());
        assertThat(TaskStatus.COMPLETED).isEqualTo(taskDto.getStatus());
    }

    @Test
    public void changeStatusWithUnauthorisedUser() {
        var currentUser = new User();
        currentUser.setName("user");
        var taskDto = new TaskDto();
        taskDto.setPerformer(new UserDto());

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task()));
        Mockito.when(taskMapper.toTaskDto(any())).thenReturn(taskDto);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        var exception = assertThrows(RequestException.class, () ->
                taskService.changeTaskStatus(TaskStatus.COMPLETED, 1L));

        Mockito.verify(taskRepository, times(1)).findById(1L);
        Mockito.verify(taskMapper, times(1)).toTaskDto(any());
        Mockito.verify(userService, times(2)).getCurrentUser();
        Mockito.verify(taskRepository, times(0)).save(any());
        assertThat("У вас недостаточно прав для этого действия").isEqualTo(exception.getMessage());
    }

}