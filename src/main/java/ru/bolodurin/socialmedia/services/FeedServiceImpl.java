package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.security.JwtAuthFilter;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final JwtAuthFilter jwtAuthFilter;
    private final UserService userService;
    private final PostService postService;

    @Override
    public Optional<Page<Post>> getFeedForUser(String token) {
        return null;
    }

}
