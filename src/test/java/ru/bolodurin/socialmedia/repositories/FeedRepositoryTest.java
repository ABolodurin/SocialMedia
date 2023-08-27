package ru.bolodurin.socialmedia.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.Role;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.services.PostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class FeedRepositoryTest {
    private static final String username = "username1";
    private static final String email = "email@mail.com";
    private static final String password = "password";
    private static final Role role = Role.USER;
    private static User user2;
    private static User user3;
    private static User user1;

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
                .posts(new ArrayList<>())
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
                .findPostsBySubscriptionsFromUser(subscriber, PostService.DEFAULT_PAGEABLE)
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
                .findPostsBySubscriptionsFromUser(subscriber, PostService.DEFAULT_PAGEABLE)
                .orElseThrow()
                .getContent();

        LocalDateTime later = LocalDateTime.now();

        for (Post earlier : actual) {
            assertThat(earlier.getTimestamp()).isBefore(later);
            later = earlier.getTimestamp();
        }

    }

}