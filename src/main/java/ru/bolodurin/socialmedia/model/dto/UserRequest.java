package ru.bolodurin.socialmedia.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @ApiModelProperty(notes = "User username")
    @NotEmpty
    private String username;

    public static UserRequest of(String username) {
        return new UserRequest(username);
    }

}
