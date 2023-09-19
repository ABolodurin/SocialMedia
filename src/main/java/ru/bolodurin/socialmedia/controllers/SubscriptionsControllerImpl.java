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
import ru.bolodurin.socialmedia.model.dto.SubsResponse;
import ru.bolodurin.socialmedia.model.dto.UserRequest;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.security.JwtService;
import ru.bolodurin.socialmedia.services.SubsService;
import ru.bolodurin.socialmedia.services.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
@Api(tags = {SwaggerConfig.SUBS_TAG})
public class SubscriptionsControllerImpl implements SubscriptionsController {
    private final SubsService subsService;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    @PutMapping("/sub")
    @ApiOperation(value = "Subscribe")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful, returns subscriptions"))
    public @ResponseBody ResponseEntity<SubsResponse> subscribe(
            @Valid @RequestBody UserRequest userToSubscribe,
            @ApiParam(value = SwaggerConfig.AUTH_ANNOTATION)
            @RequestHeader(value = "Authorization") String authHeader) {
        User user = userService.findByUsername(jwtService.extractLoginFromHeader(authHeader));

        return ResponseEntity.ok(subsService.subscribe(userToSubscribe, user));
    }

    @Override
    @PutMapping("/unsub")
    @ApiOperation(value = "Unsubscribe")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful, returns subscriptions"))
    public @ResponseBody ResponseEntity<SubsResponse> unsubscribe(
            @Valid @RequestBody UserRequest userToUnsubscribe,
            @ApiParam(value = SwaggerConfig.AUTH_ANNOTATION)
            @RequestHeader(value = "Authorization") String authHeader) {
        User user = userService.findByUsername(jwtService.extractLoginFromHeader(authHeader));

        return ResponseEntity.ok(subsService.unsubscribe(userToUnsubscribe, user));
    }

    @Override
    @GetMapping("/subscriptions")
    @ApiOperation(value = "Get current subscriptions")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful"))
    public @ResponseBody ResponseEntity<SubsResponse> getSubscriptions(
            @ApiParam(value = SwaggerConfig.AUTH_ANNOTATION)
            @RequestHeader(value = "Authorization") String authHeader) {
        User user = userService.findByUsername(jwtService.extractLoginFromHeader(authHeader));

        return ResponseEntity.ok(subsService.getSubscriptions(user));
    }

    @Override
    @GetMapping("/subscribers")
    @ApiOperation(value = "Get current subscribers")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful"))
    public @ResponseBody ResponseEntity<SubsResponse> getSubscribers(
            @ApiParam(value = SwaggerConfig.AUTH_ANNOTATION)
            @RequestHeader(value = "Authorization") String authHeader) {
        User user = userService.findByUsername(jwtService.extractLoginFromHeader(authHeader));

        return ResponseEntity.ok(subsService.getSubscribers(user));
    }

}
