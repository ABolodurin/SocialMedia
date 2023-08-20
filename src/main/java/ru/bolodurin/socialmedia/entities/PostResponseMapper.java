package ru.bolodurin.socialmedia.entities;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PostResponseMapper implements Function<Post, PostResponse> {

    @Override
    public PostResponse apply(Post post) {
        return new PostResponse(
                post.getId(),
                post.getHeader(),
                post.getContent()
        );
    }
}
