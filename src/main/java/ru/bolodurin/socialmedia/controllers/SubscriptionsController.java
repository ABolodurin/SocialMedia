package ru.bolodurin.socialmedia.controllers;

import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.model.dto.SubsResponse;
import ru.bolodurin.socialmedia.model.dto.UserRequest;

public interface SubscriptionsController {
    ResponseEntity<SubsResponse> subscribe(UserRequest userToSubscribe, String authHeader);

    ResponseEntity<SubsResponse> unsubscribe(UserRequest userToUnsubscribe, String authHeader);

    ResponseEntity<SubsResponse> getSubscriptions(String authHeader);

    ResponseEntity<SubsResponse> getSubscribers(String authHeader);

}
