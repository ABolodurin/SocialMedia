package ru.bolodurin.socialmedia.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.bolodurin.socialmedia.entities.Code;
import ru.bolodurin.socialmedia.entities.CommonException;
import ru.bolodurin.socialmedia.entities.ErrorResponse;

import java.util.Objects;

@ControllerAdvice
public class ErrorHandlingConfig {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException e){
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build(), e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e){
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .code(Code.REQUEST_VALIDATION_ERROR)
                .message(Objects.requireNonNull(e.getFieldError()).getDefaultMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(InternalAuthenticationServiceException e){
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .code(Code.AUTHENTICATION_ERROR)
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUncommonException(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .code(Code.INTERNAL_SERVER_ERROR)
                .message("Internal Service error")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
