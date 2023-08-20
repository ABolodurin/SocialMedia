package ru.bolodurin.socialmedia.services;

import org.springframework.data.domain.Page;
import ru.bolodurin.socialmedia.entities.Post;

import java.util.Optional;

public interface FeedService {
    Optional<Page<Post>> getFeedForUser(String token);

}
