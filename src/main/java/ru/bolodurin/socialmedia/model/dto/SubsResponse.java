package ru.bolodurin.socialmedia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class SubsResponse {
    @Schema(description = "List of subscriptions/subscribers")
    private List<UserResponse> users;

    public SubsResponse() {
        this.users = new LinkedList<>();
    }

}
