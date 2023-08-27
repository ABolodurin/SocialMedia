package ru.bolodurin.socialmedia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.PostResponseMapper;
import ru.bolodurin.socialmedia.entities.Role;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.repositories.FeedRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

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

        try {
            feedService.getFeedForUser(actualUser);
        } catch (NoSuchElementException ignore) {
        }

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(feedRepository).findPostsBySubscriptionsFromUser(userArgumentCaptor.capture(), any());
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(userArgumentCaptor.getValue());
    }

}
