package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.repositories.UserRepository;
import ru.bolodurin.socialmedia.security.JwtService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("username not found" + username));
    }

    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow();
    }

    @Override
    public void update(String username, User updatedUser) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow();

        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setPosts(updatedUser.getPosts());

        userRepository.save(updatedUser);
    }

    @Override
    public User findUserByHeader(String authHeader, JwtService jwtService) {
        String username = jwtService.extractLogin(
                jwtService.getTokenFromHeader(authHeader));
        return userRepository
                .findByUsername(username)
                .orElseThrow();
    }

}
