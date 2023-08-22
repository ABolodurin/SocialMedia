package ru.bolodurin.socialmedia.services;

import org.springframework.data.domain.Page;
import ru.bolodurin.socialmedia.entities.PostResponse;

public interface FeedService {
    Page<PostResponse> getFeedForUser(String authToken);

}
