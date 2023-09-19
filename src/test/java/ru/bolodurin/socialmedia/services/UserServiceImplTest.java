package ru.bolodurin.socialmedia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.Post;
import ru.bolodurin.socialmedia.model.entities.Role;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private User user;
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl(userRepository);

        user = User
                .builder()
                .username("username")
                .email("email")
                .password("password")
                .role(Role.USER)
                .posts(List.of(new Post()))
                .subscriptions(List.of(new User()))
                .subscribers(List.of(new User()))
                .build();
    }

    @Test
    void shouldFindUserByUsername() {
        String expectedUsername = user.getUsername();
        when(userRepository.findByUsername(expectedUsername)).thenReturn(Optional.of(user));

        userService.findByUsername(expectedUsername);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(userRepository).findByUsername(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(expectedUsername);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        String nonExistingUsername = "someUsername";

        assertThatThrownBy(() -> userService.findByUsername(nonExistingUsername))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(nonExistingUsername);
    }

    @Test
    void shouldAddNewUser() {
        User expected = user;

        userService.add(expected);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldUpdateUser() {
        User expected = user;
        when(userRepository.findByUsername(expected.getUsername()))
                .thenReturn(Optional.of(user));

        userService.update(expected.getUsername(), expected);

        verify(userRepository).save(expected);
    }

}
