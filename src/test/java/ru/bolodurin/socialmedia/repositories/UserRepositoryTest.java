package ru.bolodurin.socialmedia.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.Role;
import ru.bolodurin.socialmedia.entities.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
class UserRepositoryTest {
    private static final String username = "username1";
    private static final String email = "email@mail.com";
    private static final String password = "password";
    private static final Role role = Role.USER;
    private static Post post = new Post("header", "content", null);
    private static User user2;
    private static User user3;
    private static User user1;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void init() {
        postRepository.save(post);
        post = postRepository.findAll().stream().findAny().orElseThrow();

        user2 = User
                .builder()
                .username("username2")
                .email("email1@mail.com")
                .password(password)
                .role(role)
                .posts(new ArrayList<>())
                .subscriptions(new ArrayList<>())
                .subscribers(new ArrayList<>())
                .build();

        user3 = User
                .builder()
                .username("username3")
                .email("email2@mail.com")
                .password(password)
                .role(role)
                .posts(new ArrayList<>())
                .subscriptions(new ArrayList<>())
                .subscribers(new ArrayList<>())
                .build();

        user1 = User
                .builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .posts(List.of(post))
                .subscriptions(List.of(user2))
                .subscribers(List.of(user3))
                .build();

        userRepository.save(user2);
        userRepository.save(user3);
    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindUserByUsername() {
        User expected = user1;

        userRepository.save(expected);

        User actual = userRepository.findByUsername(username).orElseThrow();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void itShouldNotFindUserByUsernameThatNotExists() {
        String nonExistingUsername = "username";

        assertThatThrownBy(() -> userRepository.findByUsername(nonExistingUsername)
                .orElseThrow(() -> new UsernameNotFoundException(nonExistingUsername)))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(nonExistingUsername);

    }

}
