package ru.bolodurin.socialmedia.entities;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PostResponseMapper implements Function<Post, PostResponse> {
    @Override
    public PostResponse apply(Post post) {
        return PostResponse
                .builder()
                .id(post.getId())
                .header(post.getHeader())
                .content(post.getContent())
                .timestamp(post.getTimestamp())
                .build();
    }

}
