package ru.bolodurin.socialmedia.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.entities.AuthResponse;
import ru.bolodurin.socialmedia.entities.LoginRequest;
import ru.bolodurin.socialmedia.entities.RegisterRequest;
import ru.bolodurin.socialmedia.services.AuthenticationService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthenticationService authService;
    @Override
    @ApiOperation(value = "Receives registration form and returns bearer token")
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.of(authService.register(request));
    }

    @Override
    @ApiOperation(value = "Receives login form and returns bearer token")
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.of(authService.auth(request));
    }

}
