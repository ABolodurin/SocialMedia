package ru.bolodurin.socialmedia.services;

import ru.bolodurin.socialmedia.entities.SubsResponse;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.entities.UserRequest;

public interface SubsService {
    SubsResponse subscribe(UserRequest userToSubscribe, User user);

    SubsResponse unsubscribe(UserRequest userToUnsubscribe, User user);

    SubsResponse getSubscriptions(User user);

    SubsResponse getSubscribers(User user);

    boolean isFriends(User current, User consumer);

}
