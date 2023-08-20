package ru.bolodurin.socialmedia.services;

import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.PostRequest;

import java.util.Optional;

public interface PostService {
    Optional<PostResponse> create(String token, PostRequest post);

    Optional<PostResponse> update(String authHeader, PostResponse updatedPost);

    Optional<PostResponse> delete(String authHeader, PostResponse post);
//    Post findById(Long id);
//    Page<Post> findByUser(String username);
//    Page<Post> findForUser(String username);

}
