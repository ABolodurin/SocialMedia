package ru.bolodurin.socialmedia.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.bolodurin.socialmedia.model.entities.Message;
import ru.bolodurin.socialmedia.model.entities.Role;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.services.MessageServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class MessageRepositoryTest {
    private User user1;
    private User user2;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void init() {
        user1 = User
                .builder()
                .username("username1")
                .email("email1@mail.com")
                .password("password1")
                .role(Role.USER)
                .build();

        user2 = User
                .builder()
                .username("username2")
                .email("email2@mail.com")
                .password("password2")
                .role(Role.USER)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void chatShouldBeCorrect() throws InterruptedException {
        String[] messages = {"3", "2", "1", "0"};

        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) messageRepository.save(new Message(messages[i], user2, user1));
            if (i % 2 != 0) messageRepository.save(new Message(messages[i], user1, user2));
            Thread.sleep(10);
        }

        List<Message> actualChat = messageRepository
                .findChatBetween(user1, user2, MessageServiceImpl.DEFAULT_PAGEABLE)
                .orElseThrow()
                .getContent();

        LocalDateTime later = LocalDateTime.now();
        int expected;

        for (int i = 0; i < actualChat.size(); i++) {
            expected = i;
            Message actual = actualChat.get(i);
            LocalDateTime before = actual.getTimestamp();

            if (i % 2 == 0) {
                assertThat(actual.getConsumer()).usingRecursiveComparison().isEqualTo(user1);
                assertThat(actual.getProducer()).usingRecursiveComparison().isEqualTo(user2);
            } else {
                assertThat(actual.getConsumer()).usingRecursiveComparison().isEqualTo(user2);
                assertThat(actual.getProducer()).usingRecursiveComparison().isEqualTo(user1);
            }

            assertThat(Integer.parseInt(actual.getMessage())).isEqualTo(expected);
            assertThat(later).isAfter(before);
            later = before;
        }

    }

}
