package ru.bolodurin.socialmedia.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.entities.MessageRequest;
import ru.bolodurin.socialmedia.entities.MessageResponse;
import ru.bolodurin.socialmedia.entities.UserResponse;

public interface MessageController {
    ResponseEntity<Page<MessageResponse>> sendMessage(MessageRequest message, String authHeader);

    ResponseEntity<Page<MessageResponse>> showChatWith(UserResponse userToChat, String authHeader);

}
