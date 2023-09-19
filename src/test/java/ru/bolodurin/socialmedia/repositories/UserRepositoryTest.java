package ru.bolodurin.socialmedia.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.bolodurin.socialmedia.model.entities.Role;
import ru.bolodurin.socialmedia.model.entities.User;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
class UserRepositoryTest {
    private static final String username = "username1";
    private static final String email = "email@mail.com";
    private static final String password = "password";
    private static final Role role = Role.USER;
    private User user;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        user = User
                .builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .posts(Collections.emptyList())
                .subscriptions(Collections.emptyList())
                .subscribers(Collections.emptyList())
                .build();

        userRepository.save(user);
    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindUserByUsername() {
        User expected = user;

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
