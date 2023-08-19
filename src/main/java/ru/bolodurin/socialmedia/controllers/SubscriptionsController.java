package ru.bolodurin.socialmedia.controllers;

import ru.bolodurin.socialmedia.entities.User;

import java.util.List;

public interface SubscriptionsController {
    void subscribe(User subscriber, User user);
    void unsubscribe(User subscriber, User user);
    List<User> getSubscriptions(User subscriber);
    List<User> getSubscribers(User user);
    List<User> getFriendlist(User user);

}
