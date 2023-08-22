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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @ApiOperation(value = "Creates the user's post")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful creation"))

    @Override
    @PostMapping
    public @ResponseBody ResponseEntity<Page<PostResponse>> createPost(
            @ApiParam(value = "\"Bearer \"+ autorization token")
            @RequestHeader(value = "Authorization") String authHeader, @RequestBody PostRequest post) {
        return ResponseEntity.ok(postService.create(authHeader, post));
    }

    @ApiOperation(value = "Updates the user's post")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful update"))

    @Override
    @PutMapping
    public @ResponseBody ResponseEntity<Page<PostResponse>> updatePost(
            @ApiParam(value = "\"Bearer \"+ autorization token")
            @RequestHeader(value = "Authorization") String authHeader, @RequestBody PostResponse updatedPost) {
        return ResponseEntity.ok(postService.update(authHeader, updatedPost));
    }

    @ApiOperation(value = "Deletes the user's post")
    @ApiResponses(value =
            @ApiResponse(code = 200, message = "Successful delete"))

    @Override
    @DeleteMapping
    public @ResponseBody ResponseEntity<Page<PostResponse>> deletePost(
            @ApiParam(value = "\"Bearer \"+ autorization token")
            @RequestHeader(value = "Authorization") String authHeader, @RequestBody PostResponse post) {
        return ResponseEntity.ok(postService.delete(authHeader, post));
    }

}
