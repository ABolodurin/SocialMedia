package ru.bolodurin.socialmedia.model.mappers;

import org.springframework.stereotype.Component;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.model.dto.UserResponse;

import java.util.function.Function;

@Component
public class UserResponseMapper implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        return new UserResponse(user.getUsername());
    }

}
