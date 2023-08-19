package ru.bolodurin.socialmedia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;

    public static AuthResponse of(String token) {
        return new AuthResponse(token);
    }

}
