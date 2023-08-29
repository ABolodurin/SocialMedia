package ru.bolodurin.socialmedia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.Role;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.entities.UserRequest;
import ru.bolodurin.socialmedia.entities.UserResponse;
import ru.bolodurin.socialmedia.entities.UserResponseMapper;
import ru.bolodurin.socialmedia.repositories.SubsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubsServiceImplTest {
    private User user1;
    private User user2;
    @Mock
    private UserService userService;
    @Mock
    private SubsRepository subsRepository;
    private UserResponseMapper userResponseMapper;
    private SubsService subsService;

    @BeforeEach
    void init() {
        userResponseMapper = new UserResponseMapper();
        subsService = new SubsServiceImpl(userService, userResponseMapper, subsRepository);

        user1 = User
                .builder()
                .username("username1")
                .email("email")
                .password("password")
                .role(Role.USER)
                .posts(List.of(new Post()))
                .subscriptions(new ArrayList<>())
                .subscribers(new ArrayList<>())
                .build();

        user2 = User
                .builder()
                .username("username2")
                .email("email")
                .password("password")
                .role(Role.USER)
                .posts(List.of(new Post()))
                .subscriptions(new ArrayList<>())
                .subscribers(new ArrayList<>())
                .build();
    }

    @Test
    void userShouldSubscribe() {
        User expectedUser = user1;
        User subscription = user2;
        when(userService.findByUsername(subscription.getUsername()))
                .thenReturn(subscription);

        subsService.subscribe(UserRequest.of(subscription.getUsername()), expectedUser);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(userService).findByUsername(subscription.getUsername());
        verify(userService).update(stringArgumentCaptor.capture(), userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedUser);
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(expectedUser.getUsername());
    }

    @Test
    void userShouldUnsubscribe() {
        User expectedUser = user1;
        User subscription = user2;
        expectedUser.getSubscriptions().add(subscription);
        when(userService.findByUsername(subscription.getUsername()))
                .thenReturn(subscription);

        subsService.unsubscribe(UserRequest.of(subscription.getUsername()), expectedUser);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(userService).findByUsername(subscription.getUsername());
        verify(userService).update(stringArgumentCaptor.capture(), userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedUser);
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(expectedUser.getUsername());
    }

    @Test
    void shouldShowSubscriptionsFromUser() {
        user1.getSubscriptions().add(user2);
        List<UserResponse> expected = user1
                .getSubscriptions()
                .stream()
                .map(userResponseMapper)
                .collect(Collectors.toList());

        List<UserResponse> actual = subsService.getSubscriptions(user1).getUsers();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldShowSubscribersFromUser() {
        user1.getSubscriptions().add(user2);
        List<UserResponse> expected = user2
                .getSubscribers()
                .stream()
                .map(userResponseMapper)
                .collect(Collectors.toList());

        List<UserResponse> actual = subsService.getSubscribers(user2).getUsers();

        assertThat(actual).isEqualTo(expected);
    }

}
