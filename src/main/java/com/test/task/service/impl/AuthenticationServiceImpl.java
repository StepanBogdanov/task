package com.test.task.service.impl;

import com.test.task.model.dto.JwtAuthResponseDto;
import com.test.task.model.dto.SignInRequestDto;
import com.test.task.model.dto.SignUpRequestDto;
import com.test.task.model.entity.User;
import com.test.task.model.enums.UserRole;
import com.test.task.service.AuthenticationService;
import com.test.task.service.JwtService;
import com.test.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        var user = User.builder()
                .name(signUpRequestDto.getName())
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .role(UserRole.ROLE_USER)
                .build();
        userService.createUser(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthResponseDto(jwt);
    }

    @Override
    public JwtAuthResponseDto signIn(SignInRequestDto signInRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequestDto.getEmail(),
                signInRequestDto.getPassword()
        ));

        var user = userDetailsService.loadUserByUsername(signInRequestDto.getEmail());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthResponseDto(jwt);
    }
}
