package ru.bolodurin.socialmedia.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.model.dto.PostRequest;
import ru.bolodurin.socialmedia.model.dto.PostResponse;

public interface PostController {
    ResponseEntity<Page<PostResponse>> createPost(PostRequest post, String authHeader);

    ResponseEntity<Page<PostResponse>> updatePost(PostResponse updatedPost, String authHeader);

    ResponseEntity<Page<PostResponse>> deletePost(PostResponse post, String authHeader);

    ResponseEntity<PostResponse> show(Long id);

}
