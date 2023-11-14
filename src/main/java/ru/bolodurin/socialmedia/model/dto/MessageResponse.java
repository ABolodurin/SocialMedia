package ru.bolodurin.socialmedia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    @Schema(description = "Producer username")
    private String from;
    @Schema(description = "Consumer username")
    private String to;
    @Schema(description = "Message content")
    private String message;
    @Schema(description = "Message timestamp")
    private LocalDateTime timestamp;

}
