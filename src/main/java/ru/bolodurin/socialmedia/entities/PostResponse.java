package ru.bolodurin.socialmedia.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    @ApiModelProperty(notes = "Post ID")
    private Long id;
    @ApiModelProperty(notes = "Post header")
    private String header;
    @ApiModelProperty(notes = "Post content")
    private String content;
//    private image;
//    private timeStamp;

}