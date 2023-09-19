package ru.bolodurin.socialmedia.services;

import org.springframework.data.domain.Page;
import ru.bolodurin.socialmedia.model.dto.PostRequest;
import ru.bolodurin.socialmedia.model.dto.PostResponse;
import ru.bolodurin.socialmedia.model.entities.User;

public interface PostService {

    Page<PostResponse> create(User user, PostRequest post);

    Page<PostResponse> update(User user, PostResponse updatedPost);

    Page<PostResponse> delete(User user, PostResponse post);

    Page<PostResponse> findByUser(User user);

    PostResponse show(Long id);

}
