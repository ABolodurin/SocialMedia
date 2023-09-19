package ru.bolodurin.socialmedia.services;

import org.springframework.data.domain.Page;
import ru.bolodurin.socialmedia.model.dto.PostResponse;
import ru.bolodurin.socialmedia.model.entities.User;

public interface FeedService {
    Page<PostResponse> getFeedForUser(User user);

}
