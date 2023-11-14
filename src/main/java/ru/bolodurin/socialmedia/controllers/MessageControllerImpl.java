package ru.bolodurin.socialmedia.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.configuration.SwaggerConfig;
import ru.bolodurin.socialmedia.model.dto.MessageRequest;
import ru.bolodurin.socialmedia.model.dto.MessageResponse;
import ru.bolodurin.socialmedia.model.dto.UserResponse;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.services.MessageService;
import ru.bolodurin.socialmedia.services.UserService;

import jakarta.validation.Valid;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/messenger")
@Tag(name = SwaggerConfig.MESSENGER_TAG)
public class MessageControllerImpl implements MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @Override
    @PostMapping
    @Operation(summary = "Send message to user",
            responses = @ApiResponse(responseCode = "200", description = "Successful send"))
    public @ResponseBody ResponseEntity<Page<MessageResponse>> sendMessage(
            @Valid @RequestBody MessageRequest message, @Parameter(hidden = true) Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(messageService.sendMessage(user, message));
    }

    @Override
    @GetMapping
    @Operation(summary = "Show chat with user",
            responses = @ApiResponse(responseCode = "200", description = "Success"))
    public @ResponseBody ResponseEntity<Page<MessageResponse>> showChatWith(
            @Valid @RequestBody UserResponse userToChat, @Parameter(hidden = true) Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(messageService.getChatWith(userToChat, user));
    }

}
