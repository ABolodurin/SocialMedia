package ru.bolodurin.socialmedia.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @ApiModelProperty(notes = "Bearer token")
    private String token;

    public static AuthResponse of(String token) {
        return new AuthResponse(token);
    }

}
