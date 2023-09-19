package ru.bolodurin.socialmedia.model.mappers;

import org.springframework.stereotype.Component;
import ru.bolodurin.socialmedia.model.entities.Message;
import ru.bolodurin.socialmedia.model.dto.MessageResponse;

import java.util.function.Function;

@Component
public class MessageResponseMapper implements Function<Message, MessageResponse> {
    @Override
    public MessageResponse apply(Message message) {
        return MessageResponse
                .builder()
                .from(message.getProducer().getUsername())
                .to(message.getConsumer().getUsername())
                .message(message.getMessage())
                .timestamp(message.getTimestamp())
                .build();
    }

}
