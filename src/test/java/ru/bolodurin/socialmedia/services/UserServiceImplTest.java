package ru.bolodurin.socialmedia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bolodurin.socialmedia.TestEntityFactory;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.repositories.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private final TestEntityFactory entityFactory = TestEntityFactory.get();
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void shouldFindUserByUsername() {
        User user = entityFactory.getUser();
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
        User expected = entityFactory.getUser();

        userService.add(expected);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldUpdateUser() {
        User beforeUpdate = entityFactory.getUser();
        User afterUpdate = entityFactory.getUser();
        when(userRepository.findByUsername(beforeUpdate.getUsername()))
                .thenReturn(Optional.of(beforeUpdate));

        userService.update(beforeUpdate.getUsername(), afterUpdate);

        verify(userRepository).findByUsername(beforeUpdate.getUsername());
        verify(userRepository).save(afterUpdate);
    }

}
