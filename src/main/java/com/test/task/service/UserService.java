package com.test.task.service;

import com.test.task.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    User createUser(User user);

    User getUserByName(String userName);

    UserDetailsService userDetailService();

    User getCurrentUser();
}
