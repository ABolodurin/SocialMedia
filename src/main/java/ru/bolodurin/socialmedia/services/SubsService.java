package ru.bolodurin.socialmedia.services;

import ru.bolodurin.socialmedia.entities.SubsResponse;
import ru.bolodurin.socialmedia.entities.UserRequest;

import java.util.Optional;

public interface SubsService {
    Optional<SubsResponse> subscribe(UserRequest userToSubscribe, String authHeader);

    Optional<SubsResponse> unsubscribe(UserRequest userToUnsubscribe, String authHeader);

    Optional<SubsResponse> getSubscriptions(String authHeader);

}

