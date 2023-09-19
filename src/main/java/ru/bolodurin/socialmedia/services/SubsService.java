package ru.bolodurin.socialmedia.services;

import ru.bolodurin.socialmedia.model.dto.SubsResponse;
import ru.bolodurin.socialmedia.model.dto.UserRequest;
import ru.bolodurin.socialmedia.model.entities.User;

public interface SubsService {
    SubsResponse subscribe(UserRequest userToSubscribe, User user);

    SubsResponse unsubscribe(UserRequest userToUnsubscribe, User user);

    SubsResponse getSubscriptions(User user);

    SubsResponse getSubscribers(User user);

    boolean isFriends(User current, User consumer);

}
