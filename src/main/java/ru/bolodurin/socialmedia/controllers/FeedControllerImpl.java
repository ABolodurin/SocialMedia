package ru.bolodurin.socialmedia.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.configuration.SwaggerConfig;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.services.FeedService;

@RequestMapping("/feed")
@RestController
@RequiredArgsConstructor
@Api(tags = {SwaggerConfig.FEED_TAG})
public class FeedControllerImpl implements FeedController {
    private final FeedService feedService;

    @ApiOperation(value = "Creates the user's post")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful creation"))

    @Override
    @GetMapping
    public @ResponseBody ResponseEntity<Page<Post>> showFeed(
            @ApiParam(value = "\"Bearer \"+ autorization token")
            @RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(feedService.getFeedForUser(token).orElse(null));
    }

}
