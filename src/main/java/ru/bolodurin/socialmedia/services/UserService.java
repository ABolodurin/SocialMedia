package ru.bolodurin.socialmedia.services;

import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.security.JwtService;

public interface UserService
//        extends UserDetailsService
{
    User findByUsername(String username);

    void add(User user);

    User findByEmail(String username);

    void update(String username, User updatedUser);

    User findUserByHeader(String authHeader, JwtService jwtService);

}
