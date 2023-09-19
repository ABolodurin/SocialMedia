package ru.bolodurin.socialmedia.services;

import org.springframework.data.domain.Page;
import ru.bolodurin.socialmedia.model.dto.MessageRequest;
import ru.bolodurin.socialmedia.model.dto.MessageResponse;
import ru.bolodurin.socialmedia.model.dto.UserResponse;
import ru.bolodurin.socialmedia.model.entities.User;

public interface MessageService {

    Page<MessageResponse> sendMessage(User current, MessageRequest message);

    Page<MessageResponse> getChatWith(UserResponse userToChat, User user);

}
