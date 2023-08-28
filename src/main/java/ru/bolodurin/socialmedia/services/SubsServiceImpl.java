package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.entities.SubsResponse;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.entities.UserRequest;
import ru.bolodurin.socialmedia.entities.UserResponseMapper;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubsServiceImpl implements SubsService {
    private final UserService userService;
    private final UserResponseMapper userResponseMapper;

    @Override
    public SubsResponse subscribe(UserRequest userToSubscribe, User user) {
        User subscription = userService.findByUsername(userToSubscribe.getUsername());

        user.getSubscriptions().add(subscription);
        userService.update(user.getUsername(), user);

        return getSubscriptions(user);
    }

    @Override
    public SubsResponse unsubscribe(UserRequest userToUnsubscribe, User user) {
        User subscription = userService.findByUsername(userToUnsubscribe.getUsername());

        user.getSubscriptions().remove(subscription);
        userService.update(user.getUsername(), user);

        return getSubscriptions(user);
    }

    @Override
    public SubsResponse getSubscriptions(User user) {
        return new SubsResponse(user
                .getSubscriptions()
                .stream()
                .map(userResponseMapper)
                .collect(Collectors.toList()));
    }

    @Override
    public SubsResponse getSubscribers(User user) {
        return new SubsResponse(user
                .getSubscribers()
                .stream()
                .map(userResponseMapper)
                .collect(Collectors.toList()));
    }

}
