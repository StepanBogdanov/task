package com.test.task.mapper;

import com.test.task.model.dto.TaskDto;
import com.test.task.model.entity.Task;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class, CommentMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TaskMapper {

    TaskDto toTaskDto(Task task);

    Task toTask(TaskDto taskDto);
}
