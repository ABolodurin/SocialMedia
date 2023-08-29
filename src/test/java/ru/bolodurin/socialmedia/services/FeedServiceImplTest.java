package ru.bolodurin.socialmedia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import ru.bolodurin.socialmedia.entities.CommonException;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.PostResponseMapper;
import ru.bolodurin.socialmedia.entities.Role;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.repositories.FeedRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedServiceImplTest {
    private static User user;
    @Mock
    private FeedRepository feedRepository;
    private FeedService feedService;


    @BeforeEach
    void init() {
        this.feedService = new FeedServiceImpl(new PostResponseMapper(), feedRepository);

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
    void shouldReturnValidFeed() {
        User actualUser = user;
        when(feedRepository.findPostsBySubscriptionsFromUser(any(), any()))
                .thenReturn(Optional.of(new PageImpl<>(List.of(new Post()))));

        feedService.getFeedForUser(actualUser);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(feedRepository).findPostsBySubscriptionsFromUser(userArgumentCaptor.capture(), any());
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(userArgumentCaptor.getValue());
    }

    @Test
    void shouldThrowWhenUserHasEmptyFeed() {
        String expected = user.getUsername();

        assertThatThrownBy(() -> feedService.getFeedForUser(user))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(expected);
    }

}
