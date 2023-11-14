package ru.bolodurin.socialmedia.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.model.dto.PostResponse;

import java.security.Principal;

public interface FeedController {
    ResponseEntity<Page<PostResponse>> showFeed(Principal principal);

}
