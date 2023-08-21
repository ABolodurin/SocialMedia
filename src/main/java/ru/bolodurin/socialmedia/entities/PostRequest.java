package ru.bolodurin.socialmedia.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @ApiModelProperty(notes = "Post header")
    private String header;
    @ApiModelProperty(notes = "Post content")
    private String content;

}
