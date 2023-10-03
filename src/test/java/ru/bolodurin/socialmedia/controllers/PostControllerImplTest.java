package ru.bolodurin.socialmedia.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bolodurin.socialmedia.model.dto.PostRequest;
import ru.bolodurin.socialmedia.model.dto.PostResponse;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.services.PostService;
import ru.bolodurin.socialmedia.services.UserService;

import java.security.Principal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PostControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class PostControllerImplTest {
    private static final String PRINCIPAL_USERNAME = "username";
    private static final String VALID_AUTH_HEADER = "validHeader";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostService postService;
    @MockBean
    private UserService userService;

    private Principal principal;
    private User user;
    private PostResponse postResponse;

    @BeforeEach
    void init() {
        principal = new UserPrincipal(PRINCIPAL_USERNAME);

        user = User
                .builder()
                .username(PRINCIPAL_USERNAME)
                .build();

        postResponse = PostResponse
                .builder()
                .id(1L)
                .header("header")
                .content("content")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldReturn200WhenPostIsCreated() throws Exception {
        User expectedUser = user;

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedUser);

        PostRequest expectedPost = PostRequest
                .builder()
                .header("header")
                .content("content")
                .build();

        mvc.perform(post("/userposts")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedPost)))
                .andExpect(status().isOk());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<PostRequest> postArgumentCaptor = ArgumentCaptor.forClass(PostRequest.class);

        verify(userService).findByUsername(PRINCIPAL_USERNAME);

        verify(postService).create(userArgumentCaptor.capture(), postArgumentCaptor.capture());

        User actualUser = userArgumentCaptor.getValue();
        PostRequest actualPost = postArgumentCaptor.getValue();

        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
        assertThat(actualPost).isEqualTo(expectedPost);
    }

    @Test
    void shouldReturn200WhenPostIsUpdated() throws Exception {
        User expectedUser = user;

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedUser);

        PostResponse expectedPost = postResponse;

        mvc.perform(put("/userposts")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedPost)))
                .andExpect(status().isOk());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<PostResponse> postArgumentCaptor = ArgumentCaptor.forClass(PostResponse.class);

        verify(userService).findByUsername(PRINCIPAL_USERNAME);

        verify(postService).update(userArgumentCaptor.capture(), postArgumentCaptor.capture());

        User actualUser = userArgumentCaptor.getValue();
        PostResponse actualPost = postArgumentCaptor.getValue();

        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
        assertThat(actualPost).isEqualTo(expectedPost);
    }

    @Test
    void shouldReturn200WhenPostIsDeleted() throws Exception {
        User expectedUser = user;

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedUser);

        PostResponse expectedPost = postResponse;

        mvc.perform(delete("/userposts")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedPost)))
                .andExpect(status().isOk());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<PostResponse> postArgumentCaptor = ArgumentCaptor.forClass(PostResponse.class);

        verify(userService).findByUsername(PRINCIPAL_USERNAME);

        verify(postService).delete(userArgumentCaptor.capture(), postArgumentCaptor.capture());

        User actualUser = userArgumentCaptor.getValue();
        PostResponse actualPost = postArgumentCaptor.getValue();

        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
        assertThat(actualPost).isEqualTo(expectedPost);
    }

    @Test
    void shouldReturn200WhenShowPostById() throws Exception {
        long expectedId = 1L;

        mvc.perform(get("/userposts/" + expectedId)
                        .header("Authorization", VALID_AUTH_HEADER))
                .andExpect(status().isOk());

        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(postService).show(longArgumentCaptor.capture());

        long actualId = longArgumentCaptor.getValue();
        assertThat(actualId).isEqualTo(expectedId);
    }

}
