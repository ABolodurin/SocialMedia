package ru.bolodurin.socialmedia.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.entities.PostRequest;
import ru.bolodurin.socialmedia.entities.PostResponse;

public interface PostController {
    ResponseEntity<Page<PostResponse>> createPost(String authHeader, PostRequest post);

    ResponseEntity<Page<PostResponse>> updatePost(String authHeader, PostResponse updatedPost);

    ResponseEntity<Page<PostResponse>> deletePost(String authHeader, PostResponse post);

}
