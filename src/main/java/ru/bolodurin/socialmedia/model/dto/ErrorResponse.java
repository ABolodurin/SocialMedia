package ru.bolodurin.socialmedia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.bolodurin.socialmedia.model.entities.Code;

@Data
@Builder
public class ErrorResponse {
    @Schema(description = "Error code")
    private Code code;
    @Schema(description = "Error message")
    private String message;

}
