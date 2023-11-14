package ru.bolodurin.socialmedia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bolodurin.socialmedia.TestEntityFactory;
import ru.bolodurin.socialmedia.model.dto.UserRequest;
import ru.bolodurin.socialmedia.model.dto.UserResponse;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.model.mappers.UserResponseMapper;
import ru.bolodurin.socialmedia.repositories.SubsRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubsServiceImplTest {
    private final TestEntityFactory entityFactory = TestEntityFactory.get();
    private UserResponseMapper userResponseMapper;
    private SubsService subsService;

    @Mock
    private UserService userService;
    @Mock
    private SubsRepository subsRepository;

    @BeforeEach
    void init() {
        userResponseMapper = new UserResponseMapper();
        subsService = new SubsServiceImpl(userService, userResponseMapper, subsRepository);
    }

    @Test
    void userShouldSubscribe() {
        User expectedUser = entityFactory.getUser();
        User subscription = entityFactory.getUser();
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
        User expectedUser = entityFactory.getUser();
        User subscription = entityFactory.getUser();
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
        User user1 = entityFactory.getUser();
        User user2 = entityFactory.getUser();

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
        User user1 = entityFactory.getUser();
        User user2 = entityFactory.getUser();

        user1.getSubscriptions().add(user2);
        List<UserResponse> expected = user2
                .getSubscribers()
                .stream()
                .map(userResponseMapper)
                .collect(Collectors.toList());

        List<UserResponse> actual = subsService.getSubscribers(user2).getUsers();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldShowIsUserFriendToOther() {
        User expected1 = entityFactory.getUser();
        User expected2 = entityFactory.getUser();

        subsService.isFriends(expected1, expected2);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(subsRepository).checkIsFriends(userArgumentCaptor.capture(), userArgumentCaptor.capture());

        User actual1 = userArgumentCaptor.getAllValues().get(0);
        User actual2 = userArgumentCaptor.getAllValues().get(1);

        assertThat(actual1).usingRecursiveComparison().isEqualTo(expected1);
        assertThat(actual2).usingRecursiveComparison().isEqualTo(expected2);
    }

}
