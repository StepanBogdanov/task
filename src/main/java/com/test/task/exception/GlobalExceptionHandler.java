package com.test.task.exception;

import com.test.task.model.dto.InvalidRequestDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<InvalidRequestDataDto> handleValidException(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream().
                map(FieldError::getDefaultMessage).
                collect(Collectors.joining("\n"));
        var data = new InvalidRequestDataDto(errors);
        return ResponseEntity.badRequest().body(data);
    }

    @ExceptionHandler
    public ResponseEntity<InvalidRequestDataDto> handleRequestException(RequestException ex) {
        var data = new InvalidRequestDataDto(ex.getMessage());
        return ResponseEntity.badRequest().body(data);
    }
}
