package ru.bolodurin.socialmedia.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.configuration.SwaggerConfig;
import ru.bolodurin.socialmedia.model.dto.PostResponse;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.services.FeedService;
import ru.bolodurin.socialmedia.services.UserService;

import java.security.Principal;

@RequestMapping("/feed")
@RestController
@RequiredArgsConstructor
@Tag(name = SwaggerConfig.FEED_TAG)
public class FeedControllerImpl implements FeedController {
    private final FeedService feedService;
    private final UserService userService;

    @Override
    @GetMapping
    @Operation(summary = "Shows feed for current user",
            responses = @ApiResponse(responseCode = "200", description = "Successful creation"))
    public @ResponseBody ResponseEntity<Page<PostResponse>> showFeed(@Parameter(hidden = true) Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(feedService.getFeedForUser(user));
    }

}
