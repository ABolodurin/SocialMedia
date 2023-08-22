package ru.bolodurin.socialmedia.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.entities.PostResponse;

public interface FeedController {
    ResponseEntity<Page<PostResponse>> showFeed(String token);

}
