package ru.bolodurin.socialmedia.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.bolodurin.socialmedia.model.entities.Role;
import ru.bolodurin.socialmedia.model.entities.User;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class SubsRepositoryTest {
    private User user1;
    private User user2;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubsRepository subsRepository;

    @BeforeEach
    void init() {
        user1 = User
                .builder()
                .username("username1")
                .email("email1@mail.com")
                .password("password1")
                .role(Role.USER)
                .subscriptions(new ArrayList<>())
                .subscribers(new ArrayList<>())
                .build();

        user2 = User
                .builder()
                .username("username2")
                .email("email2@mail.com")
                .password("password2")
                .role(Role.USER)
                .subscriptions(new ArrayList<>())
                .subscribers(new ArrayList<>())
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void friendsFlagIsCorrect() {
        assertThat(subsRepository.checkIsFriends(user1, user2)).isFalse();

        user1.getSubscriptions().add(user2);
        userRepository.save(user1);
        assertThat(subsRepository.checkIsFriends(user1, user2)).isFalse();

        user2.getSubscriptions().add(user1);
        user1.getSubscriptions().clear();
        userRepository.save(user1);
        userRepository.save(user2);
        assertThat(subsRepository.checkIsFriends(user1, user2)).isFalse();

        user1.getSubscriptions().add(user2);
        userRepository.save(user1);
        assertThat(subsRepository.checkIsFriends(user1, user2)).isTrue();

        user1.getSubscriptions().add(userRepository.findByUsername(user2.getUsername()).orElseThrow());
        userRepository.save(user1);

    }

}
