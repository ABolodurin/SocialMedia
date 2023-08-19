package ru.bolodurin.socialmedia.services;

import ru.bolodurin.socialmedia.entities.AuthResponse;
import ru.bolodurin.socialmedia.entities.LoginRequest;
import ru.bolodurin.socialmedia.entities.RegisterRequest;

import java.util.Optional;

public interface AuthenticationService {

    Optional<AuthResponse> register(RegisterRequest request);

    Optional<AuthResponse> auth(LoginRequest request);
}
