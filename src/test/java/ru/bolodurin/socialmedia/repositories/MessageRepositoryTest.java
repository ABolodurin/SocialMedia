package ru.bolodurin.socialmedia.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.bolodurin.socialmedia.TestEntityFactory;
import ru.bolodurin.socialmedia.model.entities.Message;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.services.MessageServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class MessageRepositoryTest {
    private final TestEntityFactory entityFactory = TestEntityFactory.get();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @AfterEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    void chatShouldBeCorrect() throws InterruptedException {
        User user1 = entityFactory.getUser();
        userRepository.save(user1);

        User user2 = entityFactory.getUser();
        userRepository.save(user2);

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
