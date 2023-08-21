package ru.bolodurin.socialmedia.controllers;

import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.entities.AuthResponse;
import ru.bolodurin.socialmedia.entities.LoginRequest;
import ru.bolodurin.socialmedia.entities.RegisterRequest;

public interface AuthController {
    ResponseEntity<AuthResponse> register(RegisterRequest request);

    ResponseEntity<AuthResponse> login(LoginRequest request);

}
