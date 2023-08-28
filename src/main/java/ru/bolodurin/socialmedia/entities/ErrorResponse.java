package ru.bolodurin.socialmedia.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    @ApiModelProperty(notes = "Error code")
    private Code code;
    @ApiModelProperty(notes = "Error message")
    private String message;

}
