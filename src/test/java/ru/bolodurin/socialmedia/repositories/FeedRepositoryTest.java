package ru.bolodurin.socialmedia.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.bolodurin.socialmedia.model.entities.Post;
import ru.bolodurin.socialmedia.model.entities.Role;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.services.PostServiceImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class FeedRepositoryTest {
    private static final String password = "password";
    private static final Role role = Role.USER;
    private User user1;
    private User user2;
    private User user3;

    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        user2 = User
                .builder()
                .username("username2")
                .email("email2@mail.com")
                .password(password)
                .role(role)
                .posts(Collections.emptyList())
                .subscriptions(Collections.emptyList())
                .subscribers(Collections.emptyList())
                .build();

        user3 = User
                .builder()
                .username("username3")
                .email("email3@mail.com")
                .password(password)
                .role(role)
                .posts(Collections.emptyList())
                .subscriptions(Collections.emptyList())
                .subscribers(Collections.emptyList())
                .build();

        user1 = User
                .builder()
                .username("username1")
                .email("email1@mail.com")
                .password(password)
                .role(role)
                .posts(Collections.emptyList())
                .subscriptions(List.of(user2))
                .subscribers(List.of(user3))
                .build();

        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user1);
    }

    @Test
    void feedShouldLoadPostsOfSubscriptionsOnly() {
        User subscriber = user1;
        User subscription = user2;
        User other = user3;

        postRepository.save(new Post("header1", "content1", subscriber));
        postRepository.save(new Post("header2", "content2", subscription));
        postRepository.save(new Post("header3", "content3", other));
        postRepository.save(new Post("header4", "content4", subscription));

        List<Post> actual = feedRepository
                .findPostsBySubscriptionsFromUser(subscriber, PostServiceImpl.DEFAULT_PAGEABLE)
                .orElseThrow()
                .getContent();

        for (Post actualPost : actual) {
            assertThat(actualPost.getUser()).usingRecursiveComparison().isEqualTo(subscription);
        }

    }

    @Test
    void feedShouldSortPostsByTimeStampDesc() throws InterruptedException {
        User subscriber = user1;
        User subscription = user2;

        for (int i = 0; i < 5; i++) {
            postRepository.save(new Post("header" + i, "content" + i, subscription));
            Thread.sleep(10);
        }

        List<Post> actual = feedRepository
                .findPostsBySubscriptionsFromUser(subscriber, PostServiceImpl.DEFAULT_PAGEABLE)
                .orElseThrow()
                .getContent();

        LocalDateTime later = LocalDateTime.now();

        for (Post earlier : actual) {
            assertThat(earlier.getTimestamp()).isBefore(later);
            later = earlier.getTimestamp();
        }

    }

}
