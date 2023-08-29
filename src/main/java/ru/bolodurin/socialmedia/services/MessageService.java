package ru.bolodurin.socialmedia.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.bolodurin.socialmedia.entities.MessageRequest;
import ru.bolodurin.socialmedia.entities.MessageResponse;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.entities.UserResponse;

public interface MessageService {
    Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.by("timestamp").descending());

    Page<MessageResponse> sendMessage(User current, MessageRequest message);

    Page<MessageResponse> getChatWith(UserResponse userToChat, User user);

}
