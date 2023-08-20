package ru.bolodurin.socialmedia.entities;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserResponseMapper implements Function<User,UserDTO> {
    @Override
    public UserDTO apply(User product) {
        return new UserDTO(
//                product.getUsername(),
//                product.getPosts(),
//                product.getSubscriptions(),
//                product.getSubscribers()
        );
    }
}
