package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.entities.SubsResponse;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.entities.UserRequest;
import ru.bolodurin.socialmedia.entities.UserResponse;
import ru.bolodurin.socialmedia.entities.UserResponseMapper;
import ru.bolodurin.socialmedia.repositories.SubscriptionsRepository;
import ru.bolodurin.socialmedia.repositories.UserRepository;
import ru.bolodurin.socialmedia.security.JwtService;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubsServiceImpl implements SubsService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserResponseMapper userResponseMapper;
    private final SubscriptionsRepository subRepository;

    @Override
    public SubsResponse subscribe(UserRequest userToSubscribe, String authHeader) {
        User subscriber = userService.findUserByHeader(authHeader, jwtService);
        User subscription = userService.findByUsername(userToSubscribe.getUsername());

        subscriber.getSubscriptions().add(subscription);
        userRepository.save(subscriber);

        return this.getSubscriptions(authHeader);
    }

    @Override
    public SubsResponse unsubscribe(UserRequest userToUnsubscribe, String authHeader) {
        User subscriber = userService.findUserByHeader(authHeader, jwtService);
        User subscription = userService.findByUsername(userToUnsubscribe.getUsername());

        subscriber.getSubscriptions().remove(subscription);
        userRepository.save(subscriber);

        return this.getSubscriptions(authHeader);
    }

    @Override
    public SubsResponse getSubscriptions(String authHeader) {
        List<UserResponse> subscriptions = new LinkedList<>();

        userService
                .findUserByHeader(authHeader, jwtService)
                .getSubscriptions()
                .forEach(sub -> subscriptions.add(userResponseMapper.apply(sub)));

        return new SubsResponse(subscriptions);
    }

    @Override
    public SubsResponse getSubscribers(String authHeader) {
        List<UserResponse> subscribers = new LinkedList<>();

        userService
                .findUserByHeader(authHeader, jwtService)
                .getSubscribers()
                .forEach(sub -> subscribers.add(userResponseMapper.apply(sub)));

        return new SubsResponse(subscribers);
    }

    private boolean isFriend(String authHeader, User userToCheck) {
        User current = userService.findUserByHeader(authHeader, jwtService);

        return subRepository
                .findSubscriptionBySubscriber(current.getUsername(), userToCheck.getUsername()).isPresent()
                && subRepository
                        .findSubscriptionBySubscriber(userToCheck.getUsername(), current.getUsername()).isPresent();
    }

}
