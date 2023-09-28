package ru.bolodurin.socialmedia.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.configuration.SwaggerConfig;
import ru.bolodurin.socialmedia.model.dto.PostRequest;
import ru.bolodurin.socialmedia.model.dto.PostResponse;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.services.PostService;
import ru.bolodurin.socialmedia.services.UserService;

import jakarta.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userposts")
@Tag(name = SwaggerConfig.USER_POSTS_TAG)
public class PostControllerImpl implements PostController {
    private final PostService postService;
    private final UserService userService;

    @Override
    @PostMapping
    @Operation(summary = "Creates the user's post",
            responses = @ApiResponse(responseCode = "200", description = "Successful creation"))
    public @ResponseBody ResponseEntity<Page<PostResponse>> createPost(
            @Valid @RequestBody PostRequest post, @Parameter(hidden = true) Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(postService.create(user, post));
    }

    @Override
    @PutMapping
    @Operation(summary = "Updates the user's post",
            responses = @ApiResponse(responseCode = "200", description = "Successful update"))
    public @ResponseBody ResponseEntity<Page<PostResponse>> updatePost(
            @Valid @RequestBody PostResponse updatedPost, @Parameter(hidden = true) Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(postService.update(user, updatedPost));
    }

    @Override
    @DeleteMapping
    @Operation(summary = "Deletes the user's post",
            responses = @ApiResponse(responseCode = "200", description = "Successful delete"))
    public @ResponseBody ResponseEntity<Page<PostResponse>> deletePost(
            @Valid @RequestBody PostResponse post, @Parameter(hidden = true) Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(postService.delete(user, post));
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Show post by id",
            responses = @ApiResponse(responseCode = "200", description = "Success"))
    public ResponseEntity<PostResponse> show(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(postService.show(id));
    }

}
