package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.list.TreeList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.PostResponseMapper;
import ru.bolodurin.socialmedia.entities.SubsResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final SubsService subsService;
    private final UserService userService;
    private final PostResponseMapper postResponseMapper;
    @Value("${api.feed-size}")
    private int feedPageSize;

    @Override
    public Page<PostResponse> getFeedForUser(String authToken) {
        SubsResponse subscriptions = subsService.getSubscriptions(authToken);
        List<PostResponse> feedContent = new TreeList<>();

        subscriptions.getUsers().forEach(subscription ->
                userService
                        .findByUsername(subscription.getUsername())
                        .getPosts()
                        .forEach(post -> feedContent.add(postResponseMapper.apply(post))));

        return new PageImpl<>(
                feedContent,
                PostService.DEFAULT_PAGEABLE,
                feedContent.size());
    }

}
