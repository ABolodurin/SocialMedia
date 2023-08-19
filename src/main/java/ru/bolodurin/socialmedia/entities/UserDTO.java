package ru.bolodurin.socialmedia.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private List<Post> posts; // map<id,post> for check id?
    private List<User> subscriptions;
    private List<User> subscribers;

}
