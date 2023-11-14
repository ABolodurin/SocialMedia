package ru.bolodurin.socialmedia.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.bolodurin.socialmedia.TestEntityFactory;
import ru.bolodurin.socialmedia.model.entities.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class SubsRepositoryTest {
    private final TestEntityFactory entityFactory = TestEntityFactory.get();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubsRepository subsRepository;

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    void friendsFlagIsCorrect() {
        User user1 = entityFactory.getUser();
        userRepository.save(user1);

        User user2 = entityFactory.getUser();
        userRepository.save(user2);

        assertThat(subsRepository.checkIsFriends(user1, user2)).isFalse();

        user1.getSubscriptions().add(user2);
        userRepository.save(user1);
        assertThat(subsRepository.checkIsFriends(user1, user2)).isFalse();

        user1.getSubscriptions().clear();
        userRepository.save(user1);
        user2.getSubscriptions().add(user1);
        userRepository.save(user2);
        assertThat(subsRepository.checkIsFriends(user1, user2)).isFalse();

        user1.getSubscriptions().add(user2);
        userRepository.save(user1);
        assertThat(subsRepository.checkIsFriends(user1, user2)).isTrue();
    }

}
