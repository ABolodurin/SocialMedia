package ru.bolodurin.socialmedia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Schema(description = "User username")
    @NotEmpty(message = "Field must be not empty")
    private String username;

    @Schema(description = "User password")
    @NotEmpty(message = "Field must be not empty")
    private String password;

}
