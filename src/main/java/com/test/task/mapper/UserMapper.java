package com.test.task.mapper;

import com.test.task.model.dto.UserDto;
import com.test.task.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(UserDto userDto);
    UserDto toUserDto(User user);
}
