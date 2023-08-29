package ru.bolodurin.socialmedia.entities;

import org.springframework.stereotype.Component;

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
