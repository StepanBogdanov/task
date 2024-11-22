package com.test.task.repository;

import com.test.task.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByName(String userName);
    Optional<User> findUserByEmail(String email);
    boolean existsByName(String username);
    boolean existsByEmail(String email);
}
