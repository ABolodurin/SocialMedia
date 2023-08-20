package ru.bolodurin.socialmedia.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.User;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByUsername(String username);

    void add(User user);

    Optional<User> findByEmail(String username);

    void update(String username, User updatedUser);

//    List<Post> getPostsOf(String username);
//
//
//    void updatePost(Long id);
//
//    void deletePost(Long id);

}
