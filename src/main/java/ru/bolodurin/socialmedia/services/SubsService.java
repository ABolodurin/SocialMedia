package ru.bolodurin.socialmedia.services;

import ru.bolodurin.socialmedia.entities.SubsResponse;
import ru.bolodurin.socialmedia.entities.UserRequest;

public interface SubsService {
    SubsResponse subscribe(UserRequest userToSubscribe, String authHeader);

    SubsResponse unsubscribe(UserRequest userToUnsubscribe, String authHeader);

    SubsResponse getSubscriptions(String authHeader);

    SubsResponse getSubscribers(String authHeader);

}

