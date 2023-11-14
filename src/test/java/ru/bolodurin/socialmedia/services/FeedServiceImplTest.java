package ru.bolodurin.socialmedia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import ru.bolodurin.socialmedia.TestEntityFactory;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.Post;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.model.mappers.PostResponseMapper;
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
    private final TestEntityFactory entityFactory = TestEntityFactory.get();
    private FeedService feedService;

    @Mock
    private FeedRepository feedRepository;

    @BeforeEach
    void init() {
        this.feedService = new FeedServiceImpl(new PostResponseMapper(), feedRepository);
    }

    @Test
    void shouldReturnValidFeed() {
        User actualUser = entityFactory.getUser();
        when(feedRepository.findPostsBySubscriptionsFromUser(any(), any()))
                .thenReturn(Optional.of(new PageImpl<>(List.of(new Post()))));

        feedService.getFeedForUser(actualUser);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(feedRepository).findPostsBySubscriptionsFromUser(userArgumentCaptor.capture(), any());
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(userArgumentCaptor.getValue());
    }

    @Test
    void shouldThrowWhenUserHasEmptyFeed() {
        User user = entityFactory.getUser();

        assertThatThrownBy(() -> feedService.getFeedForUser(user))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(user.getUsername());
    }

}
