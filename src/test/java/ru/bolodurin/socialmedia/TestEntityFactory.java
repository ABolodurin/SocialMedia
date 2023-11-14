package ru.bolodurin.socialmedia;

import ru.bolodurin.socialmedia.model.dto.LoginRequest;
import ru.bolodurin.socialmedia.model.dto.MessageRequest;
import ru.bolodurin.socialmedia.model.dto.MessageResponse;
import ru.bolodurin.socialmedia.model.dto.PostRequest;
import ru.bolodurin.socialmedia.model.dto.PostResponse;
import ru.bolodurin.socialmedia.model.dto.RegisterRequest;
import ru.bolodurin.socialmedia.model.dto.SubsResponse;
import ru.bolodurin.socialmedia.model.dto.UserRequest;
import ru.bolodurin.socialmedia.model.dto.UserResponse;
import ru.bolodurin.socialmedia.model.entities.Code;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.Message;
import ru.bolodurin.socialmedia.model.entities.Post;
import ru.bolodurin.socialmedia.model.entities.Role;
import ru.bolodurin.socialmedia.model.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestEntityFactory {
    private static TestEntityFactory instance;

    private TestEntityFactory() {
    }

    public static TestEntityFactory get() {
        if (instance == null) instance = new TestEntityFactory();
        return instance;
    }

    public User getUser() {
        String name = "user_" + getIdentifier();

        return User
                .builder()
                .username(name)
                .email(name + "@example.com")
                .password(name)
                .role(Role.USER)
                .posts(new ArrayList<>())
                .subscriptions(new ArrayList<>())
                .subscribers(new ArrayList<>())
                .build();
    }

    public UserRequest getUserRequest() {
        String name = "userRequest_" + getIdentifier();
        return UserRequest.of(name);
    }

    public UserResponse getUserResponse() {
        String name = "userResponse_" + getIdentifier();
        return UserResponse.of(name);
    }

    public SubsResponse getSubsResponse() {
        String name = "userResponse_" + getIdentifier();
        return new SubsResponse(List.of(new UserResponse(name)));
    }

    public CommonException getCommonException() {
        String message = "message_" + getIdentifier();
        return CommonException
                .builder()
                .code(Code.TEST_CODE)
                .message(message)
                .build();
    }

    public PostResponse getPostResponse() {
        String post = "postResponse_" + getIdentifier();
        return PostResponse
                .builder()
                .id((long) (Math.random() * 50) + 1L)
                .header("header_" + post)
                .content("content_" + post)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public PostRequest getPostRequest() {
        String post = "postRequest_" + getIdentifier();
        return PostRequest
                .builder()
                .header("header_" + post)
                .content("content_" + post)
                .build();
    }

    public MessageRequest getMessageRequest() {
        String message = "messageRequest_" + getIdentifier();
        return MessageRequest
                .builder()
                .message(message)
                .build();
    }

    public MessageResponse getMessageResponse() {
        String message = "messageResponse_" + getIdentifier();
        return MessageResponse
                .builder()
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public RegisterRequest getRegisterRequest() {
        String name = "regUsername_" + getIdentifier();
        return RegisterRequest
                .builder()
                .username(name)
                .email(name + "@example.com")
                .password(name)
                .build();
    }

    public LoginRequest getLoginRequest() {
        String name = "loginUsername_" + getIdentifier();
        return LoginRequest
                .builder()
                .username(name)
                .password(name)
                .build();
    }

    public Post getPost() {
        String postName = "post_" + getIdentifier();
        Post post = new Post("header_" + postName, "content_" + postName, null);
        post.setId((long) (Math.random() * 50) + 1L);
        return post;
    }

    public Message getMessage() {
        String message = "message_" + getIdentifier();
        return new Message(message, null, null);
    }

    private static String getIdentifier() {
        Object o = new Object();
        return o.toString()
                .replace("@", "")
                .replace("java.lang.", ""); //example "Object782cc5fa"
    }

}
