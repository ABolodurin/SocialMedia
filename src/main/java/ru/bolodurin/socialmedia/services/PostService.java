package ru.bolodurin.socialmedia.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.bolodurin.socialmedia.entities.PostRequest;
import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.entities.UserResponse;

public interface PostService {
    Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.by("timestamp").descending());

    Page<PostResponse> create(User user, PostRequest post);

    Page<PostResponse> update(User user, PostResponse updatedPost);

    Page<PostResponse> delete(User user, PostResponse post);

    PostResponse findById(Long id);

    Page<PostResponse> findByUser(UserResponse user);

}
