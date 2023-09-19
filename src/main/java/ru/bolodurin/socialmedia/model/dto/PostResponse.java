package ru.bolodurin.socialmedia.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
public class PostResponse {
    @ApiModelProperty(notes = "Post ID")
    @NotEmpty
    private Long id;
    @ApiModelProperty(notes = "Post header")
    @NotEmpty
    private String header;
    @ApiModelProperty(notes = "Post content")
    @NotEmpty
    private String content;
    @ApiModelProperty(notes = "Post timestamp")
    @NotEmpty
    private LocalDateTime timestamp;

}
