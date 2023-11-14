package ru.bolodurin.socialmedia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Schema(description = "Username, uses as auth login")
    @NotEmpty(message = "Field must be not empty")
    private String username;

    @Schema(description = "User email")
    @NotEmpty
    @Email(message = "Please enter a valid email address")
    private String email;

    @Schema(description = "User password")
    @NotEmpty
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

}
