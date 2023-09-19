package ru.bolodurin.socialmedia.controllers;

import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.model.dto.AuthResponse;
import ru.bolodurin.socialmedia.model.dto.LoginRequest;
import ru.bolodurin.socialmedia.model.dto.RegisterRequest;

public interface AuthController {
    ResponseEntity<AuthResponse> register(RegisterRequest request);

    ResponseEntity<AuthResponse> login(LoginRequest request);

}
