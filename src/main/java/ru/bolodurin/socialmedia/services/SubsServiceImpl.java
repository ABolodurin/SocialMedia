package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.entities.SubsResponse;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.entities.UserRequest;
import ru.bolodurin.socialmedia.entities.UserResponse;
import ru.bolodurin.socialmedia.entities.UserResponseMapper;
import ru.bolodurin.socialmedia.repositories.UserRepository;
import ru.bolodurin.socialmedia.security.JwtService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubsServiceImpl implements SubsService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserResponseMapper userResponseMapper;

    @Override
    public Optional<SubsResponse> subscribe(UserRequest userToSubscribe, String authHeader) {
        User subscriber = userService.findUserByHeader(authHeader, jwtService);
        User subscription = userService.findByUsername(userToSubscribe.getUsername());

        subscriber.getSubscriptions().add(subscription);
        userRepository.save(subscriber);

        return Optional.of(new SubsResponse());
    }

    @Override
    public Optional<SubsResponse> unsubscribe(UserRequest userToUnsubscribe, String authHeader) {
        User subscriber = userService.findUserByHeader(authHeader, jwtService);
        User subscription = userService.findByUsername(userToUnsubscribe.getUsername());

        subscriber.getSubscriptions().remove(subscription);
        userRepository.save(subscriber);

        return Optional.of(new SubsResponse());
    }

    @Override
    public Optional<SubsResponse> getSubscriptions(String authHeader) {
        List<UserResponse> subscriptions = new LinkedList<>();

        userService
                .findUserByHeader(authHeader, jwtService)
                .getSubscriptions()
                .forEach(sub -> subscriptions.add(userResponseMapper.apply(sub)));

        return Optional.of(new SubsResponse(subscriptions));
    }

}
