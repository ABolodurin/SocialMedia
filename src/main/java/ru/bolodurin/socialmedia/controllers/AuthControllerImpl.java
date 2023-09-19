package ru.bolodurin.socialmedia.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Api(tags = {SwaggerConfig.AUTH_TAG})
public class AuthControllerImpl implements AuthController {
    private final AuthenticationService authService;

    @Override
    @PostMapping("/register")
    @ApiOperation(value = "Receives registration form and returns bearer token")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful registration"))
    public @ResponseBody ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Override
    @PostMapping("/login")
    @ApiOperation(value = "Receives login form and returns bearer token")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful login"))
    public @ResponseBody ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.auth(request));
    }

}
