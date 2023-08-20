package ru.bolodurin.socialmedia.controllers;

import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.PostRequest;

public interface PostController {
    ResponseEntity<PostResponse> createPost(String authHeader, PostRequest post);
    ResponseEntity<PostResponse> updatePost(String authHeader, PostResponse updatedPost);
    ResponseEntity<PostResponse> deletePost(String authHeader, PostResponse post);

}
