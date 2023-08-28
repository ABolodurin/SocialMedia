package ru.bolodurin.socialmedia.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private Code code;
    private String message;

}
