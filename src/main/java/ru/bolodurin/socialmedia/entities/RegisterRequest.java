package ru.bolodurin.socialmedia.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @ApiModelProperty(notes = "Username")
    private String username;
    @ApiModelProperty(notes = "User email, uses as auth login")
    private String email;
    @ApiModelProperty(notes = "User password")
    private String password;

}
