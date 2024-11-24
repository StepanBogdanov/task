package com.test.task.configuration;

import com.test.task.model.entity.User;
import com.test.task.model.enums.UserRole;
import com.test.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultUserLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByName("admin")) {
            var admin = User.builder()
                    .name("admin")
                    .email("admin")
                    .password("$2a$10$Zh76x5VwHC1mQ0o2iTUDT.Z61D.Wvfxc/Wt53RUCaU8tNgwRRTsgG")
                    .role(UserRole.ROLE_ADMIN)
                    .build();
            userRepository.save(admin);
        }
    }
}
