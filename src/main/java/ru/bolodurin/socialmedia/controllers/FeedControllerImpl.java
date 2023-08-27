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
import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.security.JwtService;
import ru.bolodurin.socialmedia.services.FeedService;
import ru.bolodurin.socialmedia.services.UserService;

@RequestMapping("/feed")
@RestController
@RequiredArgsConstructor
@Api(tags = {SwaggerConfig.FEED_TAG})
public class FeedControllerImpl implements FeedController {
    private final FeedService feedService;
    private final UserService userService;
    private final JwtService jwtService;

    @ApiOperation(value = "Shows feed for current user")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful creation"))

    @Override
    @GetMapping
    public @ResponseBody ResponseEntity<Page<PostResponse>> showFeed(
            @ApiParam(value = "\"Bearer \"+ autorization token")
            @RequestHeader(value = "Authorization") String authHeader) {
        User user = userService.findByUsername(jwtService.extractLoginFromHeader(authHeader));

        return ResponseEntity.ok(feedService.getFeedForUser(user));
    }

}
