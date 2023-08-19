package ru.bolodurin.socialmedia.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User findByName(String username);

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
