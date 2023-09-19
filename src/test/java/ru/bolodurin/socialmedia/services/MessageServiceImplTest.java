package ru.bolodurin.socialmedia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.Message;
import ru.bolodurin.socialmedia.model.dto.MessageRequest;
import ru.bolodurin.socialmedia.model.mappers.MessageResponseMapper;
import ru.bolodurin.socialmedia.model.entities.Post;
import ru.bolodurin.socialmedia.model.entities.Role;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.model.dto.UserResponse;
import ru.bolodurin.socialmedia.repositories.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {
    private User user1;
    private User user2;
    private Message message;
    private MessageService messageService;

    @Mock
    private UserService userService;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private SubsService subsService;

    @BeforeEach
    void init() {
        messageService = new MessageServiceImpl(
                userService, messageRepository, new MessageResponseMapper(), subsService);

        user1 = User
                .builder()
                .username("username1")
                .email("email")
                .password("password")
                .role(Role.USER)
                .posts(List.of(new Post()))
                .subscriptions(new ArrayList<>())
                .subscribers(new ArrayList<>())
                .build();

        user2 = User
                .builder()
                .username("username2")
                .email("email")
                .password("password")
                .role(Role.USER)
                .posts(List.of(new Post()))
                .subscriptions(new ArrayList<>())
                .subscribers(new ArrayList<>())
                .build();

        message = new Message("message", user2, user1);
    }

    @Test
    void shouldSendMessage() {
        User expectedProducer = user1;
        User expectedConsumer = user2;
        String expectedMessage = "message";

        when(subsService.isFriends(expectedProducer, expectedConsumer))
                .thenReturn(true);
        when(userService.findByUsername(expectedConsumer.getUsername()))
                .thenReturn(expectedConsumer);
        when(messageRepository.findChatBetween(any(), any(), any()))
                .thenReturn(Optional.of(new PageImpl<>(List.of(message))));

        messageService.sendMessage(expectedProducer, MessageRequest
                .builder()
                .consumer(expectedConsumer.getUsername())
                .message(expectedMessage)
                .build());

        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);

        verify(messageRepository).save(messageArgumentCaptor.capture());

        Message actual = messageArgumentCaptor.getValue();

        assertThat(actual.getProducer()).usingRecursiveComparison().isEqualTo(expectedProducer);
        assertThat(actual.getConsumer()).usingRecursiveComparison().isEqualTo(expectedConsumer);
        assertThat(actual.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldGetChat() {
        User expectedProducer = user1;
        User expectedConsumer = user2;

        when(userService.findByUsername(expectedConsumer.getUsername()))
                .thenReturn(expectedConsumer);
        when(messageRepository.findChatBetween(any(), any(), any()))
                .thenReturn(Optional.of(new PageImpl<>(List.of(message))));

        messageService.getChatWith(
                UserResponse.of(expectedConsumer.getUsername()), expectedProducer);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(messageRepository)
                .findChatBetween(userArgumentCaptor.capture(), userArgumentCaptor.capture(), any());

        User actualProducer = userArgumentCaptor.getAllValues().get(0);
        User actualConsumer = userArgumentCaptor.getAllValues().get(1);

        assertThat(actualProducer).usingRecursiveComparison().isEqualTo(expectedProducer);
        assertThat(actualConsumer).usingRecursiveComparison().isEqualTo(expectedConsumer);
    }

    @Test
    void shouldThrowIfNotFriends() {
        User expectedProducer = user1;
        User expectedConsumer = user2;
        String expectedMessage = "message";

        when(subsService.isFriends(expectedProducer, expectedConsumer))
                .thenReturn(false);
        when(userService.findByUsername(expectedConsumer.getUsername()))
                .thenReturn(expectedConsumer);

        assertThatThrownBy(() -> messageService.sendMessage(
                expectedProducer, MessageRequest
                        .builder()
                        .consumer(expectedConsumer.getUsername())
                        .message(expectedMessage)
                        .build()))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(expectedConsumer.getUsername());
    }

    @Test
    void shouldThrowWhenChatIsEmpty() {
        User expectedProducer = user1;
        User expectedConsumer = user2;

        when(userService.findByUsername(expectedConsumer.getUsername()))
                .thenReturn(expectedConsumer);
        when(messageRepository.findChatBetween(any(), any(), any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> messageService.getChatWith(
                UserResponse.of(expectedConsumer.getUsername()), expectedProducer))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(expectedConsumer.getUsername())
                .hasMessageContaining(expectedProducer.getUsername());
    }

}
