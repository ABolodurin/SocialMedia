package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.PostResponseMapper;
import ru.bolodurin.socialmedia.entities.PostRequest;
import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.repositories.PostRepository;
import ru.bolodurin.socialmedia.security.JwtService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final PostResponseMapper postDTOMapper;

    @Override
    public Optional<PostResponse> create(String token, PostRequest post) {
        String username = jwtService.extractLogin(
                jwtService.getTokenFromheader(token));
        User user = userService.findByUsername(username).orElseThrow();

        postRepository.save(Post
                .builder()
                .header(post.getHeader())
                .content(post.getContent())
                .user(user)
                .build());

        Post postedPost = postRepository.findByHeader(post.getHeader()).orElse((new Post("error","error", user)));
        return Optional.ofNullable(postDTOMapper.apply(postedPost));
    }

}
