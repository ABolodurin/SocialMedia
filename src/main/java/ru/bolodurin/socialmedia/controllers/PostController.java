package ru.bolodurin.socialmedia.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.model.dto.PostRequest;
import ru.bolodurin.socialmedia.model.dto.PostResponse;

import java.security.Principal;

public interface PostController {
    ResponseEntity<Page<PostResponse>> createPost(PostRequest post, Principal principal);

    ResponseEntity<Page<PostResponse>> updatePost(PostResponse updatedPost, Principal principal);

    ResponseEntity<Page<PostResponse>> deletePost(PostResponse post, Principal principal);

    ResponseEntity<PostResponse> show(Long id);

}
