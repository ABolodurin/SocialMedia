package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.entities.Code;
import ru.bolodurin.socialmedia.entities.CommonException;
import ru.bolodurin.socialmedia.entities.Message;
import ru.bolodurin.socialmedia.entities.MessageRequest;
import ru.bolodurin.socialmedia.entities.MessageResponse;
import ru.bolodurin.socialmedia.entities.MessageResponseMapper;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.entities.UserResponse;
import ru.bolodurin.socialmedia.repositories.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
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
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();

        messageRepository.save(new Message(
                current, consumer, message.getMessage()));

        return getChatWith(UserResponse.of(consumer.getUsername()), current);
    }

    @Override
    public Page<MessageResponse> getChatWith(UserResponse userToChat, User user) {
        User consumer = userService.findByUsername(userToChat.getUsername());

        return messageRepository.findChatBetween(user, consumer, MessageService.DEFAULT_PAGEABLE)
                .orElseThrow(() -> CommonException
                        .builder()
                        .code(Code.NOT_FOUND)
                        .message("No messages found between " + user.getUsername() +
                                " and " + consumer.getUsername())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .map(messageResponseMapper);
    }

}
