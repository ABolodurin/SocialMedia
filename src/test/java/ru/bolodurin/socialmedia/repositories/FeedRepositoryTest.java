package ru.bolodurin.socialmedia.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.bolodurin.socialmedia.TestEntityFactory;
import ru.bolodurin.socialmedia.model.entities.Post;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.services.PostServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class FeedRepositoryTest {
    private final TestEntityFactory entityFactory = TestEntityFactory.get();
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    void feedShouldLoadPostsOfSubscriptionsOnly() {
        User subscription = entityFactory.getUser();
        userRepository.save(subscription);

        User other = entityFactory.getUser();
        userRepository.save(other);

        User subscriber = entityFactory.getUser();
        subscriber.getSubscriptions().add(subscription);
        userRepository.save(subscriber);

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
        User subscription = entityFactory.getUser();
        userRepository.save(subscription);

        User subscriber = entityFactory.getUser();
        subscriber.getSubscriptions().add(subscription);
        userRepository.save(subscriber);

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
