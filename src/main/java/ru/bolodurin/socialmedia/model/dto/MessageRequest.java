package ru.bolodurin.socialmedia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    @Schema(description = "Consumer username")
    @NotEmpty(message = "Field must be not empty")
    private String consumer;
    @Schema(description = "Message content")
    @NotEmpty(message = "Field must be not empty")
    private String message;

}
