package ru.bolodurin.socialmedia.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@RequiredArgsConstructor
public class CommonException extends RuntimeException {
    private final Code code;
    private final String message;
    private final HttpStatus httpStatus;

}
