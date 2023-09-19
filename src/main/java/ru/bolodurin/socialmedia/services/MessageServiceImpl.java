package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.model.dto.MessageRequest;
import ru.bolodurin.socialmedia.model.dto.MessageResponse;
import ru.bolodurin.socialmedia.model.dto.UserResponse;
import ru.bolodurin.socialmedia.model.entities.Code;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.Message;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.model.mappers.MessageResponseMapper;
import ru.bolodurin.socialmedia.repositories.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    public static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.by("timestamp").descending());
    private final UserService userService;
    private final MessageRepository messageRepository;
    private final MessageResponseMapper messageResponseMapper;
    private final SubsService subsService;

    @Override
    public Page<MessageResponse> sendMessage(User current, MessageRequest message) {
        User consumer = userService.findByUsername(message.getConsumer());

        if (!(subsService.isFriends(current, consumer)))
            throw CommonException
                    .builder()
                    .code(Code.NOT_FRIENDS)
                    .message(consumer.getUsername() + " is not your friend")
                    .build();

        messageRepository.save(new Message(
                message.getMessage(), consumer, current));

        return getChatWith(UserResponse.of(consumer.getUsername()), current);
    }

    @Override
    public Page<MessageResponse> getChatWith(UserResponse userToChat, User user) {
        User consumer = userService.findByUsername(userToChat.getUsername());

        return messageRepository.findChatBetween(user, consumer, DEFAULT_PAGEABLE)
                .orElseThrow(() -> CommonException
                        .builder()
                        .code(Code.NOT_FOUND)
                        .message("No messages found between " + user.getUsername() +
                                " and " + consumer.getUsername())
                        .build())
                .map(messageResponseMapper);
    }

}
