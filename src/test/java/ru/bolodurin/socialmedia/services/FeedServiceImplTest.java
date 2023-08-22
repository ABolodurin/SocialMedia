package ru.bolodurin.socialmedia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.PostResponseMapper;
import ru.bolodurin.socialmedia.entities.SubsResponse;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.entities.UserResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedServiceImplTest {
    private static final String VALID_TOKEN = "adsgaljq8yuq34h3[q49y8h";
    private static User user1;
    private static User user2;
    @Mock
    private SubsService subsService;
    @Mock
    private UserService userService;
    private FeedService feedService;


    @BeforeEach
    void init() throws InterruptedException {
        this.feedService = new FeedServiceImpl(subsService, userService, new PostResponseMapper());

        user1 = User
                .builder()
                .username("username1")
                .posts(new ArrayList<>())
                .build();

        user2 = User
                .builder()
                .username("username2")
                .posts(new ArrayList<>())
                .build();

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) user1.getPosts().add(new Post());
            else user2.getPosts().add(new Post());

            Thread.sleep(10);
        }

    }

    @Test
    void shouldReturnValidFeed() {
        when(subsService.getSubscriptions(VALID_TOKEN))
                .thenReturn(new SubsResponse(List.of(
                        UserResponse.of(user1.getUsername()),
                        UserResponse.of(user2.getUsername()))));

        when(userService.findByUsername(user1.getUsername()))
                .thenReturn(user1);
        when(userService.findByUsername(user2.getUsername()))
                .thenReturn(user2);

        Page<PostResponse> actual = feedService.getFeedForUser(VALID_TOKEN);

        LocalDateTime later = LocalDateTime.now();

        for (PostResponse earlier: actual)  {
            assertThat(earlier.getTimestamp()).isBefore(later);
            later = earlier.getTimestamp();
        }

    }

}
