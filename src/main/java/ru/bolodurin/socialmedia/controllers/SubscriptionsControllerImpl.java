package ru.bolodurin.socialmedia.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.configuration.SwaggerConfig;
import ru.bolodurin.socialmedia.entities.SubsResponse;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.entities.UserRequest;
import ru.bolodurin.socialmedia.security.JwtService;
import ru.bolodurin.socialmedia.services.SubsService;
import ru.bolodurin.socialmedia.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
@Api(tags = {SwaggerConfig.SUBS_TAG})
public class SubscriptionsControllerImpl implements SubscriptionsController {
    private final SubsService subsService;
    private final UserService userService;
    private final JwtService jwtService;

    @ApiOperation(value = "Subscribe")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful, returns subscriptions"))

    @Override
    @PutMapping("/sub")
    public @ResponseBody ResponseEntity<SubsResponse> subscribe(
            @RequestBody UserRequest userToSubscribe,
            @ApiParam(value = "\"Bearer \"+ autorization token")
            @RequestHeader(value = "Authorization") String authHeader) {
        User user = userService.findByUsername(jwtService.extractLoginFromHeader(authHeader));

        return ResponseEntity.ok(subsService.subscribe(userToSubscribe, user));
    }

    @ApiOperation(value = "Unsubscribe")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful, returns subscriptions"))

    @Override
    @PutMapping("/unsub")
    public @ResponseBody ResponseEntity<SubsResponse> unsubscribe(
            @RequestBody UserRequest userToUnsubscribe,
            @ApiParam(value = "\"Bearer \"+ autorization token")
            @RequestHeader(value = "Authorization") String authHeader) {
        User user = userService.findByUsername(jwtService.extractLoginFromHeader(authHeader));

        return ResponseEntity.ok(subsService.unsubscribe(userToUnsubscribe, user));
    }

    @ApiOperation(value = "Get current subscriptions")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful"))

    @Override
    @GetMapping("/subscriptions")
    public @ResponseBody ResponseEntity<SubsResponse> getSubscriptions(
            @ApiParam(value = "\"Bearer \"+ autorization token")
            @RequestHeader(value = "Authorization") String authHeader) {
        User user = userService.findByUsername(jwtService.extractLoginFromHeader(authHeader));

        return ResponseEntity.ok(subsService.getSubscriptions(user));
    }

    @ApiOperation(value = "Get current subscribers")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful"))

    @Override
    @GetMapping("/subscribers")
    public @ResponseBody ResponseEntity<SubsResponse> getSubscribers(
            @ApiParam(value = "\"Bearer \"+ autorization token")
            @RequestHeader(value = "Authorization") String authHeader) {
        User user = userService.findByUsername(jwtService.extractLoginFromHeader(authHeader));

        return ResponseEntity.ok(subsService.getSubscribers(user));
    }

}
