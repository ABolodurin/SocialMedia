package ru.bolodurin.socialmedia.controllers;

import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.model.dto.SubsResponse;
import ru.bolodurin.socialmedia.model.dto.UserRequest;

import java.security.Principal;

public interface SubscriptionsController {
    ResponseEntity<SubsResponse> subscribe(UserRequest userToSubscribe, Principal principal);

    ResponseEntity<SubsResponse> unsubscribe(UserRequest userToUnsubscribe, Principal principal);

    ResponseEntity<SubsResponse> getSubscriptions(Principal principal);

    ResponseEntity<SubsResponse> getSubscribers(Principal principal);

}
