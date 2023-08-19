package ru.bolodurin.socialmedia.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.User;

import java.util.List;

@RequestMapping("/feed")
@RestController
public class FeedControllerImpl implements FeedController{

    @Override
    @GetMapping
    public Page<Post> showFeed(User user) {
        return new PageImpl<>(List.of(new Post()), PageRequest.of(0,10),1);
    }

}
