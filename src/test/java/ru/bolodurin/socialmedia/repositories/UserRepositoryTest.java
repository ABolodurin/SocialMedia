package ru.bolodurin.socialmedia.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.bolodurin.socialmedia.TestEntityFactory;
import ru.bolodurin.socialmedia.model.entities.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
class UserRepositoryTest {
    private final TestEntityFactory entityFactory = TestEntityFactory.get();
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindUserByUsername() {
        User expected = entityFactory.getUser();

        userRepository.save(expected);

        User actual = userRepository.findByUsername(expected.getUsername()).orElseThrow();

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
