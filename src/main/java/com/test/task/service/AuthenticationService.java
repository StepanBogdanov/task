package com.test.task.service;

import com.test.task.model.dto.JwtAuthResponseDto;
import com.test.task.model.dto.SignInRequestDto;
import com.test.task.model.dto.SignUpRequestDto;

public interface AuthenticationService {

    JwtAuthResponseDto signUp(SignUpRequestDto signUpRequestDto);

    JwtAuthResponseDto signIn(SignInRequestDto signInRequestDto);
}
