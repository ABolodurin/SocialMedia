package ru.bolodurin.socialmedia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostResponse {
    @Schema(description = "Post ID")
    @Min(value = 1L)
    private Long id;
    @Schema(description = "Post header")
    @NotEmpty
    private String header;
    @Schema(description = "Post content")
    @NotEmpty
    private String content;
    @Schema(description = "Post timestamp")
    @NotNull
    private LocalDateTime timestamp;

}
