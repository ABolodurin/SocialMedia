package ru.bolodurin.socialmedia.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.socialmedia.configuration.SwaggerConfig;
import ru.bolodurin.socialmedia.entities.SubsResponse;
import ru.bolodurin.socialmedia.entities.UserRequest;
import ru.bolodurin.socialmedia.services.SubsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
@Api(tags = {SwaggerConfig.SUBS_TAG})
public class SubscriptionsControllerImpl implements SubscriptionsController{
    private final SubsService subsService;
    @ApiOperation(value = "Subscribe")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful subscription"))

    @Override
    @PutMapping("/sub")
    public @ResponseBody ResponseEntity<SubsResponse> subscribe(
            @RequestBody UserRequest userToSubscribe,
            @ApiParam(value = "\"Bearer \"+ autorization token")
            @RequestHeader(value = "Authorization") String authHeader) {
        return ResponseEntity.ok(subsService.subscribe(userToSubscribe, authHeader));
    }

    @ApiOperation(value = "Unsubscribe")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful unsubscription"))

    @Override
    @PutMapping("/unsub")
    public @ResponseBody ResponseEntity<SubsResponse> unsubscribe(
            @RequestBody UserRequest userToUnsubscribe,
            @ApiParam(value = "\"Bearer \"+ autorization token")
            @RequestHeader(value = "Authorization") String authHeader) {
        return ResponseEntity.ok(subsService.unsubscribe(userToUnsubscribe, authHeader));
    }

    @ApiOperation(value = "Get current subscriptions")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successful"))

    @Override
    public @ResponseBody ResponseEntity<SubsResponse> getSubscriptions(
            @ApiParam(value = "\"Bearer \"+ autorization token")
            @RequestHeader(value = "Authorization") String authHeader) {
        return ResponseEntity.ok(subsService.getSubscriptions(authHeader));
    }

}
