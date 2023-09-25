package ru.bolodurin.socialmedia.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/messenger")
@Api(tags = {SwaggerConfig.MESSENGER_TAG})
public class MessageControllerImpl implements MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @Override
    @PostMapping
    @ApiOperation(value = "Send message to user")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful send"))
    public @ResponseBody ResponseEntity<Page<MessageResponse>> sendMessage(
            @Valid @RequestBody MessageRequest message, @ApiIgnore Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(messageService.sendMessage(user, message));
    }

    @Override
    @GetMapping
    @ApiOperation(value = "Show chat with user")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Success"))
    public @ResponseBody ResponseEntity<Page<MessageResponse>> showChatWith(
            @Valid @RequestBody UserResponse userToChat, @ApiIgnore Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(messageService.getChatWith(userToChat, user));
    }

}
