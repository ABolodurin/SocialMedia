package ru.bolodurin.socialmedia.services;

import ru.bolodurin.socialmedia.entities.AuthResponse;
import ru.bolodurin.socialmedia.entities.LoginRequest;
import ru.bolodurin.socialmedia.entities.RegisterRequest;

public interface AuthenticationService {
    AuthResponse register(RegisterRequest request);

    AuthResponse auth(LoginRequest request);

}
