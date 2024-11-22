package com.test.task.controller.impl;

import com.test.task.controller.AuthController;
import com.test.task.model.dto.JwtAuthResponseDto;
import com.test.task.model.dto.SignInRequestDto;
import com.test.task.model.dto.SignUpRequestDto;
import com.test.task.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthenticationService authenticationService;

    @Override
    public JwtAuthResponseDto signUp(SignUpRequestDto requestDto) {
        return authenticationService.signUp(requestDto);
    }

    @Override
    public JwtAuthResponseDto signIn(SignInRequestDto requestDto) {
        return authenticationService.signIn(requestDto);
    }
}
