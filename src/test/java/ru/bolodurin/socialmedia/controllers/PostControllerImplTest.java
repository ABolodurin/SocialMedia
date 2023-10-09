package ru.bolodurin.socialmedia.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bolodurin.socialmedia.model.dto.PostRequest;
import ru.bolodurin.socialmedia.model.dto.PostResponse;
import ru.bolodurin.socialmedia.model.entities.Code;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.security.JwtService;
import ru.bolodurin.socialmedia.services.PostService;
import ru.bolodurin.socialmedia.services.PostServiceImpl;
import ru.bolodurin.socialmedia.services.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostControllerImpl.class)
@MockBeans(value = @MockBean(JwtService.class))
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
    private PostRequest postRequest;
    private CommonException commonException;

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

        postRequest = PostRequest
                .builder()
                .header("header")
                .content("content")
                .build();

        commonException = CommonException
                .builder()
                .code(Code.UPDATE_NON_OWN_ENTITY_ERROR)
                .message("message")
                .build();
    }

    @Test
    void shouldReturn200WhenPostIsCreated() throws Exception {
        User expectedUser = user;
        PostResponse expectedResponse = postResponse;
        PostRequest expectedPost = postRequest;

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedUser);
        given(postService.create(any(), any())).willReturn(new PageImpl<>(
                List.of(expectedResponse), PostServiceImpl.DEFAULT_PAGEABLE, 1));

        mvc.perform(post("/userposts")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.notNullValue()))
                .andExpect(jsonPath("$.pageable", Matchers.notNullValue()))
                .andExpect(jsonPath("$.content[0].id", Matchers.is(expectedResponse.getId().intValue())))
                .andExpect(jsonPath("$.content[0].header", Matchers.is(expectedResponse.getHeader())))
                .andExpect(jsonPath("$.content[0].content", Matchers.is(expectedResponse.getContent())))
                .andExpect(jsonPath("$.content[0].timestamp",
                        Matchers.startsWith(expectedResponse.getTimestamp()
                                .toString()
                                .substring(0, 25))));

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
        PostResponse expectedPost = postResponse;

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedUser);
        given(postService.update(any(), any())).willReturn(new PageImpl<>(
                List.of(expectedPost), PostServiceImpl.DEFAULT_PAGEABLE, 1));

        mvc.perform(put("/userposts")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.notNullValue()))
                .andExpect(jsonPath("$.pageable", Matchers.notNullValue()))
                .andExpect(jsonPath("$.content[0].id", Matchers.is(expectedPost.getId().intValue())))
                .andExpect(jsonPath("$.content[0].header", Matchers.is(expectedPost.getHeader())))
                .andExpect(jsonPath("$.content[0].content", Matchers.is(expectedPost.getContent())))
                .andExpect(jsonPath("$.content[0].timestamp",
                        Matchers.startsWith(expectedPost.getTimestamp()
                                .toString()
                                .substring(0, 25))));

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
        PostResponse expectedPost = postResponse;

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedUser);
        given(postService.delete(any(), any())).willReturn(new PageImpl<>(
                List.of(expectedPost), PostServiceImpl.DEFAULT_PAGEABLE, 1));

        mvc.perform(delete("/userposts")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.notNullValue()))
                .andExpect(jsonPath("$.pageable", Matchers.notNullValue()))
                .andExpect(jsonPath("$.content[0].id", Matchers.is(expectedPost.getId().intValue())))
                .andExpect(jsonPath("$.content[0].header", Matchers.is(expectedPost.getHeader())))
                .andExpect(jsonPath("$.content[0].content", Matchers.is(expectedPost.getContent())))
                .andExpect(jsonPath("$.content[0].timestamp",
                        Matchers.startsWith(expectedPost.getTimestamp()
                                .toString()
                                .substring(0, 25))));

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
        PostResponse expectedPost = postResponse;

        given(postService.show(any())).willReturn(expectedPost);

        mvc.perform(get("/userposts/" + expectedId)
                        .header("Authorization", VALID_AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(expectedPost.getId().intValue())))
                .andExpect(jsonPath("$.header", Matchers.is(expectedPost.getHeader())))
                .andExpect(jsonPath("$.content", Matchers.is(expectedPost.getContent())))
                .andExpect(jsonPath("$.timestamp",
                        Matchers.startsWith(expectedPost.getTimestamp()
                                .toString()
                                .substring(0, 25))));

        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(postService).show(longArgumentCaptor.capture());

        long actualId = longArgumentCaptor.getValue();
        assertThat(actualId).isEqualTo(expectedId);
    }

    @ParameterizedTest
    @CsvSource({" ,content", "header, "})
    void shouldValidatePostRequest(String header, String content) throws Exception {
        PostRequest postRequest = PostRequest
                .builder()
                .header(header)
                .content(content)
                .build();

        mvc.perform(post("/userposts")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(postRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(Code.REQUEST_VALIDATION_ERROR))));
    }

    @ParameterizedTest
    @CsvSource({
            " ,header,content,2023-10-06T11:51:48.382489900",
            "-1,header,content,2023-10-06T11:51:48.382489900",
            "1, ,content,2023-10-06T11:51:48.382489900",
            "1,header, ,2023-10-06T11:51:48.382489900",
            "1,header,content, ",})
    void shouldValidatePostResponse(Long id, String header, String content, LocalDateTime timestamp) throws Exception {
        PostResponse postResponse = PostResponse
                .builder()
                .id(id)
                .header(header)
                .content(content)
                .timestamp(timestamp)
                .build();

        mvc.perform(put("/userposts")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(postResponse)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(Code.REQUEST_VALIDATION_ERROR))));

        mvc.perform(delete("/userposts")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(postResponse)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(Code.REQUEST_VALIDATION_ERROR))));
    }

    @Test
    void shouldReturnCorrectCreateError() throws Exception {
        CommonException expected = commonException;
        when(postService.create(any(), any())).thenThrow(expected);

        mvc.perform(post("/userposts")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(postRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(expected.getCode()))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

    @Test
    void shouldReturnCorrectUpdateError() throws Exception {
        CommonException expected = commonException;
        when(postService.update(any(), any())).thenThrow(expected);

        mvc.perform(put("/userposts")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(postResponse)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(expected.getCode()))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

    @Test
    void shouldReturnCorrectDeleteError() throws Exception {
        CommonException expected = commonException;
        when(postService.delete(any(), any())).thenThrow(expected);

        mvc.perform(delete("/userposts")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(postResponse)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(expected.getCode()))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

    @Test
    void shouldReturnCorrectShowError() throws Exception {
        CommonException expected = commonException;
        when(postService.show(any())).thenThrow(expected);

        mvc.perform(get("/userposts/" + 1L)
                        .header("Authorization", VALID_AUTH_HEADER))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(expected.getCode()))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

}
