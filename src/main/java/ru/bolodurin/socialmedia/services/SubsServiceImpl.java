package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.model.dto.SubsResponse;
import ru.bolodurin.socialmedia.model.dto.UserRequest;
import ru.bolodurin.socialmedia.model.entities.Code;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.model.mappers.UserResponseMapper;
import ru.bolodurin.socialmedia.repositories.SubsRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubsServiceImpl implements SubsService {
    private final UserService userService;
    private final UserResponseMapper userResponseMapper;
    private final SubsRepository subsRepository;

    @Override
    public SubsResponse subscribe(UserRequest userToSubscribe, User user) {
        User subscription = userService.findByUsername(userToSubscribe.getUsername());

        user.getSubscriptions().add(subscription);

        try {
            userService.update(user.getUsername(), user);
        } catch (DataIntegrityViolationException e) {
            throw CommonException
                    .builder()
                    .code(Code.ALREADY_DONE)
                    .message(userToSubscribe.getUsername() + " is already your friend")
                    .build();
        }

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

    @Override
    public boolean isFriends(User user, User other) {
        return subsRepository.checkIsFriends(user, other);
    }

}
