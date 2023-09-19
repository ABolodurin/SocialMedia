package ru.bolodurin.socialmedia.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    @ApiModelProperty(value = "User username")
    private String username;

    public static UserResponse of(String username) {
        return new UserResponse(username);
    }

}