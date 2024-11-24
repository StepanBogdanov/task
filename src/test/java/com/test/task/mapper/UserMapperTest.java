package com.test.task.mapper;

import com.test.task.model.dto.UserDto;
import com.test.task.model.entity.User;
import com.test.task.model.enums.UserRole;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapperImpl();

    @Test
    public void shouldProperlyUserDtoToUser() {
        var userDto = new UserDto(
                1,
                "userName",
                "userMail",
                "password",
                UserRole.ROLE_USER
        );

        var user = userMapper.toUser(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userDto.getId());
        assertThat(user.getName()).isEqualTo(userDto.getName());
        assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(user.getPassword()).isEqualTo(userDto.getPassword());
        assertThat(user.getRole()).isEqualTo(userDto.getRole());
    }

    @Test
    public void shouldProperlyUserToUserDto() {
        var user = new User(
                1,
                "userName",
                "userMail",
                "password",
                UserRole.ROLE_USER
        );

        var userDto = userMapper.toUserDto(user);

        assertThat(userDto).isNotNull();
        assertThat(userDto.getId()).isEqualTo(user.getId());
        assertThat(userDto.getName()).isEqualTo(user.getName());
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDto.getPassword()).isEqualTo(user.getPassword());
        assertThat(userDto.getRole()).isEqualTo(user.getRole());
    }

}