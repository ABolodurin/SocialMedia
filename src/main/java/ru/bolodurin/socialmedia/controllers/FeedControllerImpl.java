package ru.bolodurin.socialmedia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.entities.AuthRequest;
import ru.bolodurin.socialmedia.entities.Post;

import java.util.List;

@RequestMapping("/feed")
@RestController
@RequiredArgsConstructor
public class FeedControllerImpl implements FeedController {

    @Override
    @GetMapping
    public @ResponseBody ResponseEntity<Page<Post>> showFeed(
            @RequestBody AuthRequest request) {
        return ResponseEntity.ok(
                new PageImpl<>(List.of(new Post("testPost", request.getToken())), PageRequest.of(0, 10), 1));
    }

}
