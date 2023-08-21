package ru.bolodurin.socialmedia.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @ApiModelProperty(notes = "User email")
    @NotEmpty
    @Email
    private String email;
    @ApiModelProperty(notes = "User password")
    @NotEmpty
    private String password;

}
