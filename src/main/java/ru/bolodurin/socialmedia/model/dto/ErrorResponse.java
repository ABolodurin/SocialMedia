package ru.bolodurin.socialmedia.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import ru.bolodurin.socialmedia.model.entities.Code;

@Data
@Builder
public class ErrorResponse {
    @ApiModelProperty(notes = "Error code")
    private Code code;
    @ApiModelProperty(notes = "Error message")
    private String message;

}
