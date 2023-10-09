package ru.bolodurin.socialmedia.controllers;

import com.sun.security.auth.UserPrincipal;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import ru.bolodurin.socialmedia.model.dto.PostResponse;
import ru.bolodurin.socialmedia.model.entities.Code;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.security.JwtService;
import ru.bolodurin.socialmedia.services.FeedService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedControllerImpl.class)
@MockBeans(value = @MockBean(JwtService.class))
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class FeedControllerImplTest {
    private static final String PRINCIPAL_USERNAME = "username";
    private static final String VALID_AUTH_HEADER = "validHeader";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private FeedService feedService;
    @MockBean
    private UserService userService;

    private Principal principal;
    private User user;
    private CommonException commonException;

    @BeforeEach
    void init() {
        principal = new UserPrincipal(PRINCIPAL_USERNAME);

        user = User
                .builder()
                .username(PRINCIPAL_USERNAME)
                .build();

        commonException = CommonException
                .builder()
                .code(Code.FEED_IS_EMPTY)
                .message("message")
                .build();
    }

    @Test
    void shouldReturn200WhenFeedIsShown() throws Exception {
        User expectedUser = user;
        PostResponse expectedPost = PostResponse
                .builder()
                .id(1L)
                .header("header")
                .content("content")
                .timestamp(LocalDateTime.now())
                .build();

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedUser);
        given(feedService.getFeedForUser(expectedUser)).willReturn(new PageImpl<>(
                List.of(expectedPost), PostServiceImpl.DEFAULT_PAGEABLE, 1));

        mvc.perform(get("/feed")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER))
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

        verify(userService).findByUsername(PRINCIPAL_USERNAME);

        verify(feedService).getFeedForUser(userArgumentCaptor.capture());

        User actualUser = userArgumentCaptor.getValue();

        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @Test
    void shouldReturnCorrectFeedError() throws Exception {
        CommonException expected = commonException;
        when(feedService.getFeedForUser(any())).thenThrow(expected);

        mvc.perform(get("/feed")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(expected.getCode()))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

    @Test
    void shouldReturnServiceErrorWhenThrowUncommonException() throws Exception {
        RuntimeException expected = new RuntimeException();
        when(feedService.getFeedForUser(any())).thenThrow(expected);

        mvc.perform(get("/feed")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(Code.INTERNAL_SERVER_ERROR))))
                .andExpect(jsonPath("$.message", Matchers.is("Service error")));
    }

}
