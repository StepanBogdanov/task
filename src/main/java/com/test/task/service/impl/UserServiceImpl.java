package com.test.task.service.impl;

import com.test.task.exception.RequestException;
import com.test.task.model.entity.User;
import com.test.task.repository.UserRepository;
import com.test.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {

        if (userRepository.existsByName(user.getUsername())) {
            throw new RequestException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RequestException("Пользователь с таким email уже существует");
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserByName(String userName) {
        return userRepository.findUserByName(userName).
                orElseThrow(() -> new RequestException(String.format("Пользователь с именем %s не найден", userName)));
    }

    @Override
    public UserDetailsService userDetailService() {
        return this::getUserByName;
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
    }
}
