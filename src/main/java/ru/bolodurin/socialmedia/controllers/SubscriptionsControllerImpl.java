package ru.bolodurin.socialmedia.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.configuration.SwaggerConfig;
import ru.bolodurin.socialmedia.model.dto.SubsResponse;
import ru.bolodurin.socialmedia.model.dto.UserRequest;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.services.SubsService;
import ru.bolodurin.socialmedia.services.UserService;

import jakarta.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
@Tag(name = SwaggerConfig.SUBS_TAG)
public class SubscriptionsControllerImpl implements SubscriptionsController {
    private final SubsService subsService;
    private final UserService userService;

    @Override
    @PutMapping("/sub")
    @Operation(summary = "Subscribe",
            responses = @ApiResponse(responseCode = "200", description = "Successful, returns subscriptions"))
    public @ResponseBody ResponseEntity<SubsResponse> subscribe(
            @Valid @RequestBody UserRequest userToSubscribe, @Parameter(hidden = true) Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(subsService.subscribe(userToSubscribe, user));
    }

    @Override
    @PutMapping("/unsub")
    @Operation(summary = "Unsubscribe",
            responses = @ApiResponse(responseCode = "200", description = "Successful, returns subscriptions"))
    public @ResponseBody ResponseEntity<SubsResponse> unsubscribe(
            @Valid @RequestBody UserRequest userToUnsubscribe, @Parameter(hidden = true) Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(subsService.unsubscribe(userToUnsubscribe, user));
    }

    @Override
    @GetMapping("/subscriptions")
    @Operation(summary = "Get current subscriptions",
            responses = @ApiResponse(responseCode = "200", description = "Successful"))
    public @ResponseBody ResponseEntity<SubsResponse> getSubscriptions(@Parameter(hidden = true) Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(subsService.getSubscriptions(user));
    }

    @Override
    @GetMapping("/subscribers")
    @Operation(summary = "Get current subscribers",
            responses = @ApiResponse(responseCode = "200", description = "Successful"))
    public @ResponseBody ResponseEntity<SubsResponse> getSubscribers(@Parameter(hidden = true) Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(subsService.getSubscribers(user));
    }

}
