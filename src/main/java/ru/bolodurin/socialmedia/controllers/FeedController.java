package ru.bolodurin.socialmedia.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.entities.Post;

public interface FeedController {
    ResponseEntity<Page<Post>> showFeed(String token);

}
