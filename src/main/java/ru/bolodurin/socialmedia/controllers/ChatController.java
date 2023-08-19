package ru.bolodurin.socialmedia.controllers;


import ru.bolodurin.socialmedia.entities.Message;
import ru.bolodurin.socialmedia.entities.User;

import java.util.List;

public interface ChatController {
    /*
    JWT
     */
    List<Message> getChat(User from, User to);
    void sendMessage(User from, User to, Message msg);

}
