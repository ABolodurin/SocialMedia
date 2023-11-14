package ru.bolodurin.socialmedia.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.bolodurin.socialmedia.TestEntityFactory;
import ru.bolodurin.socialmedia.model.entities.Post;
import ru.bolodurin.socialmedia.model.entities.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PostRepositoryTest {
    private final TestEntityFactory entityFactory = TestEntityFactory.get();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    void postsShouldBeOrderedByTimestampDesc() throws InterruptedException {
        User user = entityFactory.getUser();
        userRepository.save(user);

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
