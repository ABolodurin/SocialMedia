package ru.bolodurin.socialmedia.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.bolodurin.socialmedia.model.dto.ErrorResponse;
import ru.bolodurin.socialmedia.model.entities.Code;
import ru.bolodurin.socialmedia.model.entities.CommonException;

import java.util.Objects;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException e) {
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .code(Code.REQUEST_VALIDATION_ERROR)
                .message(Objects.requireNonNull(e.getFieldError()).getDefaultMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationException e) {
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .code(Code.AUTHENTICATION_ERROR)
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUncommonException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .code(Code.INTERNAL_SERVER_ERROR)
                .message("Service error")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
