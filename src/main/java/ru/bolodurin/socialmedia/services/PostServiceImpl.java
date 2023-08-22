package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.PostRequest;
import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.PostResponseMapper;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.entities.UserResponse;
import ru.bolodurin.socialmedia.repositories.PostRepository;
import ru.bolodurin.socialmedia.security.JwtService;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final PostResponseMapper postResponseMapper;

    @Override
    public Page<PostResponse> create(String authHeader, PostRequest post) {
        User user = userService.findUserByHeader(authHeader, jwtService);

        postRepository.save(new Post(post.getHeader(), post.getContent(), user));

        return this.findByUser(UserResponse.of(user.getUsername()));
    }

    @Override
    @Transactional
    public Page<PostResponse> update(String authHeader, PostResponse updatedPost) {
        Post post = postRepository.findById(updatedPost.getId()).orElseThrow();

        if (!isOfACurrentUser(authHeader, post)) throw new RuntimeException();

        post.setHeader(updatedPost.getHeader());
        post.setContent(updatedPost.getContent());
        postRepository.save(post);

        return this.findByUser(UserResponse.of(jwtService.extractLogin(authHeader)));
    }

    @Override
    public Page<PostResponse> delete(String authHeader, PostResponse postResponse) {
        Post post = postRepository.findById(postResponse.getId()).orElseThrow();

        if (!isOfACurrentUser(authHeader, post))
            throw new RuntimeException("You can't delete post which is not yours. PostID: " + post.getId());

        postRepository.deleteById(post.getId());

        return this.findByUser(UserResponse.of(jwtService.extractLogin(authHeader)));
    }

    @Override
    public PostResponse findById(Long id) {
        return postRepository
                .findById(id)
                .map(postResponseMapper)
                .orElseThrow();
    }

    @Override
    public Page<PostResponse> findByUser(UserResponse userResponse) {
        User user = userService.findByUsername(userResponse.getUsername());

        return postRepository
                .findAllByUserOrderByTimestampDesc(user, PostService.DEFAULT_PAGEABLE)
                .orElseThrow()
                .map(postResponseMapper);
    }


    private boolean isOfACurrentUser(String authHeader, Post post) {
        String username = jwtService.extractLogin(
                jwtService.getTokenFromHeader(authHeader));

        return post.getUser().getUsername().equals(username);
    }

}
