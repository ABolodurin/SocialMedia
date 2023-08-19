package ru.bolodurin.socialmedia.controllers;

import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.PostDTO;
import ru.bolodurin.socialmedia.entities.User;

public interface PostController {
    void createPost(User user, Post post);
    void updatePost(User user, PostDTO post); // or post ID
    void deletePost(User user, PostDTO post); // or post ID

}
