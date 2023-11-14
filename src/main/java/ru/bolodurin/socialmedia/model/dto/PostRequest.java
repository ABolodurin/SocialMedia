package ru.bolodurin.socialmedia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @Schema(description = "Post header")
    @NotEmpty(message = "Header must be not empty")
    private String header;
    @Schema(description = "Post content")
    @NotEmpty(message = "Content must be not empty")
    private String content;

}
