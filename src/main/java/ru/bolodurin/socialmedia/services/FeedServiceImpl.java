package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.entities.Code;
import ru.bolodurin.socialmedia.entities.CommonException;
import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.PostResponseMapper;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.repositories.FeedRepository;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final PostResponseMapper postResponseMapper;
    private final FeedRepository feedRepository;

    @Override
    public Page<PostResponse> getFeedForUser(User user) {
        return feedRepository
                .findPostsBySubscriptionsFromUser(user, PostService.DEFAULT_PAGEABLE)
                .orElseThrow(() -> CommonException
                        .builder()
                        .code(Code.FEED_IS_EMPTY)
                        .message("feed for " + user.getUsername() + " is empty")
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .map(postResponseMapper);
    }

}
