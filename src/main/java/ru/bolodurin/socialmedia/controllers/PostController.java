package ru.bolodurin.socialmedia.controllers;

import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.PostRequest;

public interface PostController {
    ResponseEntity<PostResponse> createPost(String token, PostRequest post);
//    void updatePost(User user, PostDTO post); // or post ID
//    void deletePost(User user, PostDTO post); // or post ID

}
