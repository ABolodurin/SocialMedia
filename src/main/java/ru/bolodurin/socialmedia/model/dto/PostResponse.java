package ru.bolodurin.socialmedia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
public class PostResponse {
    @Schema(description = "Post ID")
    @NotEmpty
    private Long id;
    @Schema(description = "Post header")
    @NotEmpty
    private String header;
    @Schema(description = "Post content")
    @NotEmpty
    private String content;
    @Schema(description = "Post timestamp")
    @NotEmpty
    private LocalDateTime timestamp;

}
