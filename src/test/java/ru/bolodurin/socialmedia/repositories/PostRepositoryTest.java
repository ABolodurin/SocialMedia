package ru.bolodurin.socialmedia.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.bolodurin.socialmedia.model.entities.Post;
import ru.bolodurin.socialmedia.model.entities.Role;
import ru.bolodurin.socialmedia.model.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PostRepositoryTest {
    private static final String username = "username1";
    private static final String email = "email@mail.com";
    private static final String password = "password";
    private static final Role role = Role.USER;
    private User user;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void init() {
        user = User
                .builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .posts(new ArrayList<>())
                .build();

        userRepository.save(user);
    }

    @Test
    void postsShouldBeOrderedByTimestampDesc() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            postRepository.save(new Post("header" + i, "content" + i, user));
            Thread.sleep(10);
        }

        List<Post> actual = postRepository
                .findAllByUserOrderByTimestampDesc(user, PageRequest.ofSize(5))
                .orElseThrow().getContent();

        LocalDateTime later = LocalDateTime.now();

        for (Post earlier : actual) {
            assertThat(earlier.getTimestamp()).isBefore(later);
            later = earlier.getTimestamp();
        }

    }

}
