package ru.bolodurin.socialmedia.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse implements Comparable<PostResponse>{
    @ApiModelProperty(notes = "Post ID")
    private Long id;
    @ApiModelProperty(notes = "Post header")
    private String header;
    @ApiModelProperty(notes = "Post content")
    private String content;
    @ApiModelProperty(notes = "Post content")
    private LocalDateTime timestamp;

    @Override
    public int compareTo(PostResponse o) {
        return o.timestamp.compareTo(this.timestamp);
    }
    
}
