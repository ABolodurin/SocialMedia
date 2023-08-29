package ru.bolodurin.socialmedia.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostResponse {
    @ApiModelProperty(notes = "Post ID")
    private Long id;
    @ApiModelProperty(notes = "Post header")
    private String header;
    @ApiModelProperty(notes = "Post content")
    private String content;
    @ApiModelProperty(notes = "Post content")
    private LocalDateTime timestamp;

}
