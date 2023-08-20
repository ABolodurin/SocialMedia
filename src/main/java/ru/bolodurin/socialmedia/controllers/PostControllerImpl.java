package ru.bolodurin.socialmedia.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.configuration.SwaggerConfig;
import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.PostRequest;
import ru.bolodurin.socialmedia.services.PostService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userposts")
@Api(tags = {SwaggerConfig.POST_TAG})
public class PostControllerImpl implements PostController{
    private final PostService postService;
    @Override
    @ApiOperation(value = "Creates the user's post")
    @ApiResponses(value = {
            @ApiResponse(code = 100, message = "100 is the message"), // what?
            @ApiResponse(code = 200, message = "Successful creation")
    })
    @PostMapping
    public @ResponseBody ResponseEntity<PostResponse> createPost(
            @RequestHeader(value = "Authorization") String authHeader,
            @RequestBody PostRequest post) {
        Optional<PostResponse> postResponse = postService.create(authHeader, post);
        return ResponseEntity.ok(postResponse.orElse(null));
    }


}
