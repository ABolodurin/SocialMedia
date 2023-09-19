package ru.bolodurin.socialmedia.model.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@RequiredArgsConstructor
public class CommonException extends RuntimeException {
    private final Code code;
    private final String message;

}
