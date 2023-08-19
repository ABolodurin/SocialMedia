package ru.bolodurin.socialmedia.entities;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PostDTOMapper implements Function<Post, PostDTO> {

    @Override
    public PostDTO apply(Post post) {
        return new PostDTO(
//                post.getId(),
//                post.getHeader(),
//                post.getContent()
////                post.getImage(),
////                post.getTimeStamp
        );
    }
}
