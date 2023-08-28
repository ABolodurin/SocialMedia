package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.entities.Code;
import ru.bolodurin.socialmedia.entities.CommonException;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> CommonException
                        .builder()
                        .code(Code.NOT_FOUND)
                        .message(username + " not found")
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .build());
    }

    @Override
    public void add(User user) {
        userRepository.save(user);
    }


    @Override
    public void update(String username, User updatedUser) {
        User user = findByUsername(username);

        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setSubscriptions(updatedUser.getSubscriptions());
        user.setSubscribers(updatedUser.getSubscribers());

        userRepository.save(updatedUser);
    }

}
