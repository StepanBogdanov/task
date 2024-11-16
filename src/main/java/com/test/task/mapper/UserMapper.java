package com.test.task.mapper;

import com.test.task.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.security.core.userdetails.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(UserDto userDto);

    UserDto toUserDto(User user);
}
