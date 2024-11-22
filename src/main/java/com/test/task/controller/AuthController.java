package com.test.task.controller;

import com.test.task.model.dto.JwtAuthResponseDto;
import com.test.task.model.dto.SignInRequestDto;
import com.test.task.model.dto.SignUpRequestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthController {

    @PostMapping("/sign-up")
    JwtAuthResponseDto signUp(@Valid @RequestBody SignUpRequestDto requestDto);

    @PostMapping("/sign-in")
    JwtAuthResponseDto signIn(@Valid @RequestBody SignInRequestDto requestDto);
}
