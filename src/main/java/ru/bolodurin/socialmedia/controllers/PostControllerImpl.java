package ru.bolodurin.socialmedia.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.configuration.SwaggerConfig;
import ru.bolodurin.socialmedia.model.dto.PostRequest;
import ru.bolodurin.socialmedia.model.dto.PostResponse;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.security.JwtService;
import ru.bolodurin.socialmedia.services.PostService;
import ru.bolodurin.socialmedia.services.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userposts")
@Api(tags = {SwaggerConfig.USER_POSTS_TAG})
public class PostControllerImpl implements PostController {
    private final PostService postService;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    @PostMapping
    @ApiOperation(value = "Creates the user's post")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful creation"))
    public @ResponseBody ResponseEntity<Page<PostResponse>> createPost(
            @Valid @RequestBody PostRequest post,
            @ApiParam(value = SwaggerConfig.AUTH_ANNOTATION)
            @RequestHeader(value = "Authorization") String authHeader) {
        User user = userService.findByUsername(jwtService.extractLoginFromHeader(authHeader));

        return ResponseEntity.ok(postService.create(user, post));
    }

    @Override
    @PutMapping
    @ApiOperation(value = "Updates the user's post")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful update"))
    public @ResponseBody ResponseEntity<Page<PostResponse>> updatePost(
            @Valid @RequestBody PostResponse updatedPost,
            @ApiParam(value = SwaggerConfig.AUTH_ANNOTATION)
            @RequestHeader(value = "Authorization") String authHeader) {
        User user = userService.findByUsername(jwtService.extractLoginFromHeader(authHeader));

        return ResponseEntity.ok(postService.update(user, updatedPost));
    }

    @Override
    @DeleteMapping
    @ApiOperation(value = "Deletes the user's post")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful delete"))
    public @ResponseBody ResponseEntity<Page<PostResponse>> deletePost(
            @Valid @RequestBody PostResponse post,
            @ApiParam(value = SwaggerConfig.AUTH_ANNOTATION)
            @RequestHeader(value = "Authorization") String authHeader) {
        User user = userService.findByUsername(jwtService.extractLoginFromHeader(authHeader));

        return ResponseEntity.ok(postService.delete(user, post));
    }

    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "Show post by id")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Success"))
    public ResponseEntity<PostResponse> show(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(postService.show(id));
    }

}
