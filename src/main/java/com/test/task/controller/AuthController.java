package com.test.task.controller;

import com.test.task.model.dto.InvalidRequestDataDto;
import com.test.task.model.dto.JwtAuthResponseDto;
import com.test.task.model.dto.SignInRequestDto;
import com.test.task.model.dto.SignUpRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
@Tag(name = "Сервис аутентификации")
public interface AuthController {

    @PostMapping("/sign-up")
    @Operation(summary = "Регистрация пользователя", description = """
            В теле запроса передается запрос на регистрацию пользователя SignUpRequestDto.
            Переданный запрос валидируется и создается новый пользователь.
            В случае успеха возвращается токен jwt и код 200,
            в противном случае в теле ответа возвращается описание ошибки и код 400.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Ошибка регистрации пользователя", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) }),
    })
    JwtAuthResponseDto signUp(@Valid @RequestBody SignUpRequestDto requestDto);

    @PostMapping("/sign-in")
    @Operation(summary = "Авторизация пользователя", description = """
            В теле запроса передается запрос на авторизацию пользователя SignInRequestDto.
            По переданному запросу производится попытка аутентификации пользователя.
            В случае успеха возвращается токен jwt и код 200,
            в противном случае в теле ответа возвращается описание ошибки и код 400.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно аутентифицирован"),
            @ApiResponse(responseCode = "400", description = "Ошибка аутентификации пользователя", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) }),
    })
    JwtAuthResponseDto signIn(@Valid @RequestBody SignInRequestDto requestDto);
}
