package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.model.dto.AuthResponse;
import ru.bolodurin.socialmedia.model.dto.LoginRequest;
import ru.bolodurin.socialmedia.model.dto.RegisterRequest;
import ru.bolodurin.socialmedia.model.entities.Code;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.Role;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userService.isExist(request.getUsername())) throw CommonException
                .builder()
                .code(Code.REQUEST_VALIDATION_ERROR)
                .message("user " + request.getUsername() + " is already exist")
                .build();

        User user = User
                .builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userService.add(user);

        return getResponseFor(user);
    }

    @Override
    public AuthResponse auth(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        return getResponseFor(userService.findByUsername(request.getUsername()));
    }

    private AuthResponse getResponseFor(User user) {
        String token = jwtService.generateToken(user);

        return AuthResponse.of(token);
    }

}
