package ru.bolodurin.socialmedia.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @ApiModelProperty(notes = "Username")
    @NotEmpty
    private String username;
    @ApiModelProperty(notes = "User email, uses as auth login")
    @NotEmpty
    @Email
    private String email;
    @ApiModelProperty(notes = "User password")
    @NotEmpty
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

}
