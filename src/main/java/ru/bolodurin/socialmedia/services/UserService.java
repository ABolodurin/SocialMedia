package ru.bolodurin.socialmedia.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.bolodurin.socialmedia.entities.User;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByUsername(String username);

    void add(User user);

    Optional<User> findByEmail(String username);

//    List<Post> getPostsOf(String username);
//
//    void createPost(Post post);
//
//    void updatePost(Long id);
//
//    void deletePost(Long id);

}
