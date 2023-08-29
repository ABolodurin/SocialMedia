package ru.bolodurin.socialmedia.entities;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "Producer username")
    private String from;
    @ApiModelProperty(notes = "Consumer username")
    private String to;
    @ApiModelProperty(notes = "Message content")
    private String message;
    @ApiModelProperty(notes = "Message timestamp")
    private LocalDateTime timestamp;

}
