package com.test.task.mapper;

import com.test.task.model.dto.CommentDto;
import com.test.task.model.dto.UserDto;
import com.test.task.model.entity.Comment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.security.core.userdetails.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = UserMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {

    CommentDto toCommentDto(Comment comment);

    Comment toComment(CommentDto commentDto);
}
