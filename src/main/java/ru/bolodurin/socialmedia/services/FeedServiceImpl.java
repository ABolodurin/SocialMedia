package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.model.dto.PostResponse;
import ru.bolodurin.socialmedia.model.entities.Code;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.model.mappers.PostResponseMapper;
import ru.bolodurin.socialmedia.repositories.FeedRepository;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final PostResponseMapper postResponseMapper;
    private final FeedRepository feedRepository;

    @Override
    public Page<PostResponse> getFeedForUser(User user) {
        return feedRepository
                .findPostsBySubscriptionsFromUser(user, PostServiceImpl.DEFAULT_PAGEABLE)
                .orElseThrow(() -> CommonException
                        .builder()
                        .code(Code.FEED_IS_EMPTY)
                        .message("feed for " + user.getUsername() + " is empty")
                        .build())
                .map(postResponseMapper);
    }

}
