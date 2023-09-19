package ru.bolodurin.socialmedia.services;

import ru.bolodurin.socialmedia.model.dto.AuthResponse;
import ru.bolodurin.socialmedia.model.dto.LoginRequest;
import ru.bolodurin.socialmedia.model.dto.RegisterRequest;

public interface AuthenticationService {
    AuthResponse register(RegisterRequest request);

    AuthResponse auth(LoginRequest request);

}
