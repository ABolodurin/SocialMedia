package ru.bolodurin.socialmedia.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class SubsResponse {
    @ApiModelProperty(notes = "List of subscriptions/subscribers")
    private List<UserResponse> users;

    public SubsResponse() {
        this.users = new LinkedList<>();
    }

}
