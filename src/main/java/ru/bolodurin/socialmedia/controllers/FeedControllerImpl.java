package ru.bolodurin.socialmedia.controllers;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.configuration.SwaggerConfig;
import ru.bolodurin.socialmedia.entities.AuthRequest;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.services.FeedService;

import java.util.List;

@RequestMapping("/feed")
@RestController
@RequiredArgsConstructor
@Api(tags = {SwaggerConfig.FEED_TAG})
public class FeedControllerImpl implements FeedController {
    private final FeedService feedService;

    @Override
    @GetMapping
    public @ResponseBody ResponseEntity<Page<Post>> showFeed
            (@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(feedService.getFeedForUser(token).orElse(null));
    }

}
