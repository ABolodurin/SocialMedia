package ru.bolodurin.socialmedia.services;

import ru.bolodurin.socialmedia.model.entities.User;

public interface UserService {
    User findByUsername(String username);

    void add(User user);

    void update(String username, User updatedUser);

    boolean isExist(String username);

}
