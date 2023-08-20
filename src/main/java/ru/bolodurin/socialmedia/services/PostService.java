package ru.bolodurin.socialmedia.services;

import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.PostRequest;

import java.util.Optional;

public interface PostService {
    Optional<PostResponse> create(String token, PostRequest post);
//    Post findById(Long id);
//    Page<Post> findByUser(String username);
//    Page<Post> findForUser(String username);

}
