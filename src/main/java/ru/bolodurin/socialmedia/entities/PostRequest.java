package ru.bolodurin.socialmedia.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @ApiModelProperty(notes = "Post header")
    @NotEmpty(message = "Header must be not empty")
    private String header;
    @NotEmpty(message = "Content must be not empty")
    @ApiModelProperty(notes = "Post content")
    private String content;

}
