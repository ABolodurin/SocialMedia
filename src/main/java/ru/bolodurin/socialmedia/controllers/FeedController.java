package ru.bolodurin.socialmedia.controllers;

import org.springframework.data.domain.Page;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.User;

public interface FeedController {
//    @RequestBody
    Page<Post> showFeed(User user);
}
