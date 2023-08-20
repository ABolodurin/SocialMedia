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
import ru.bolodurin.socialmedia.entities.AuthResponse;
import ru.bolodurin.socialmedia.entities.LoginRequest;
import ru.bolodurin.socialmedia.entities.RegisterRequest;
import ru.bolodurin.socialmedia.services.AuthenticationService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Api(value = "User authorization resource")
public class AuthControllerImpl implements AuthController {
    private final AuthenticationService authService;
    @Override
    @ApiOperation(value = "Receives registration form and returns bearer token")
    @ApiResponses(value = {
            @ApiResponse(code = 100,message = "100 is the message"), // what?
            @ApiResponse(code = 200, message = "Successful registration")
    })
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.of(authService.register(request));
    }

    @Override
    @ApiOperation(value = "Receives login form and returns bearer token")
    @ApiResponses(value = {
            @ApiResponse(code = 100,message = "100 is the message"), // what?
            @ApiResponse(code = 200, message = "Successful login")
    })
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.of(authService.auth(request));
    }

}
