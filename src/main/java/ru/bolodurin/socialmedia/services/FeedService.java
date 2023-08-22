package ru.bolodurin.socialmedia.services;

import org.springframework.data.domain.Page;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.PostResponse;

import java.util.Optional;

public interface FeedService {
    Page<PostResponse> getFeedForUser(String authToken);

}
