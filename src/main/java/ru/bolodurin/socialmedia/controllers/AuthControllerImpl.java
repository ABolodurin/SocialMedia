package ru.bolodurin.socialmedia.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.configuration.SwaggerConfig;
import ru.bolodurin.socialmedia.model.dto.AuthResponse;
import ru.bolodurin.socialmedia.model.dto.LoginRequest;
import ru.bolodurin.socialmedia.model.dto.RegisterRequest;
import ru.bolodurin.socialmedia.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = SwaggerConfig.AUTH_TAG)
public class AuthControllerImpl implements AuthController {
    private final AuthenticationService authService;

    @Override
    @PostMapping("/register")
    @Operation(summary = "Receives registration form and returns bearer token",
            responses = @ApiResponse(responseCode = "200", description = "Successful registration"))
    public @ResponseBody ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Override
    @PostMapping("/login")
    @Operation(summary = "Receives login form and returns bearer token",
            responses = @ApiResponse(responseCode = "200", description = "Successful login"))
    public @ResponseBody ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.auth(request));
    }

}
