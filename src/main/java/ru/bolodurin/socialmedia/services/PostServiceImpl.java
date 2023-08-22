package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.PostRequest;
import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.PostResponseMapper;
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
    private final PostResponseMapper postResponseMapper;

    @Override
    public Optional<PostResponse> create(String authHeader, PostRequest post) {
        User user = userService.findUserByHeader(authHeader, jwtService);

        postRepository.save(new Post(post.getHeader(), post.getContent(), user));

        Post postedPost = postRepository.findByHeader(post.getHeader()).orElse((new Post()));
        return Optional.ofNullable(postResponseMapper.apply(postedPost));
    }

    @Override
    @Transactional
    public Optional<PostResponse> update(String authHeader, PostResponse updatedPost) {
        Post post = postRepository.findById(updatedPost.getId()).orElseThrow();

        if (!isOfACurrentUser(authHeader, post)) return Optional.empty();

        post.setHeader(updatedPost.getHeader());
        post.setContent(updatedPost.getContent());
        postRepository.save(post);

        return postRepository.findById(updatedPost.getId()).map(postResponseMapper);
    }

    @Override
    public Optional<PostResponse> delete(String authHeader, PostResponse postResponse) {
        Post post = postRepository.findById(postResponse.getId()).orElseThrow();

        if (!isOfACurrentUser(authHeader, post))
            throw new RuntimeException("You can't delete post which is not yours. PostID: " + post.getId());

        postRepository.deleteById(post.getId());
        return Optional.empty();
    }

    private boolean isOfACurrentUser(String authHeader, Post post) {
        String username = jwtService.extractLogin(
                jwtService.getTokenFromHeader(authHeader));

        return post.getUser().getUsername().equals(username);
    }

}
