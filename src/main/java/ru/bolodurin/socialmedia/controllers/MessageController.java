package ru.bolodurin.socialmedia.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.bolodurin.socialmedia.model.dto.MessageRequest;
import ru.bolodurin.socialmedia.model.dto.MessageResponse;
import ru.bolodurin.socialmedia.model.dto.UserResponse;

public interface MessageController {
    ResponseEntity<Page<MessageResponse>> sendMessage(MessageRequest message, String authHeader);

    ResponseEntity<Page<MessageResponse>> showChatWith(UserResponse userToChat, String authHeader);

}
