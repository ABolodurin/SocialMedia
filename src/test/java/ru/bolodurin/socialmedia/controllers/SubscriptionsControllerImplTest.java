package ru.bolodurin.socialmedia.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bolodurin.socialmedia.TestEntityFactory;
import ru.bolodurin.socialmedia.model.dto.SubsResponse;
import ru.bolodurin.socialmedia.model.dto.UserRequest;
import ru.bolodurin.socialmedia.model.entities.Code;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.security.JwtService;
import ru.bolodurin.socialmedia.services.SubsService;
import ru.bolodurin.socialmedia.services.UserService;

import java.security.Principal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubscriptionsControllerImpl.class)
@MockBeans(value = @MockBean(JwtService.class))
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SubscriptionsControllerImplTest {
    private static final String PRINCIPAL_USERNAME = "username";
    private static final String VALID_AUTH_HEADER = "validHeader";
    private static final Principal PRINCIPAL = new UserPrincipal(PRINCIPAL_USERNAME);

    private final TestEntityFactory entityFactory = TestEntityFactory.get();

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SubsService subsService;
    @MockBean
    private UserService userService;

    @Test
    void shouldReturn200WhenSubscribed() throws Exception {
        User expectedUser = entityFactory.getUser();
        UserRequest expectedSub = entityFactory.getUserRequest();
        SubsResponse expectedResponse = entityFactory.getSubsResponse();

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedUser);
        given(subsService.subscribe(any(), any())).willReturn(expectedResponse);

        mvc.perform(put("/subscriptions/sub")
                        .principal(PRINCIPAL)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedSub)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", Matchers.notNullValue()))
                .andExpect(jsonPath("$.users[0].username", Matchers.is(expectedResponse
                        .getUsers()
                        .get(0)
                        .getUsername())));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<UserRequest> requestArgumentCaptor = ArgumentCaptor.forClass(UserRequest.class);

        verify(userService).findByUsername(PRINCIPAL_USERNAME);

        verify(subsService).subscribe(requestArgumentCaptor.capture(), userArgumentCaptor.capture());

        User actualUser = userArgumentCaptor.getValue();
        UserRequest actualSub = requestArgumentCaptor.getValue();

        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
        assertThat(actualSub).isEqualTo(expectedSub);
    }

    @Test
    void shouldReturn200WhenUnsubscribed() throws Exception {
        User expectedUser = entityFactory.getUser();
        UserRequest expectedSub = entityFactory.getUserRequest();
        SubsResponse expectedResponse = entityFactory.getSubsResponse();

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedUser);
        given(subsService.unsubscribe(any(), any())).willReturn(expectedResponse);

        mvc.perform(put("/subscriptions/unsub")
                        .principal(PRINCIPAL)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedSub)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", Matchers.notNullValue()))
                .andExpect(jsonPath("$.users[0].username", Matchers.is(expectedResponse
                        .getUsers()
                        .get(0)
                        .getUsername())));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<UserRequest> requestArgumentCaptor = ArgumentCaptor.forClass(UserRequest.class);

        verify(userService).findByUsername(PRINCIPAL_USERNAME);

        verify(subsService).unsubscribe(requestArgumentCaptor.capture(), userArgumentCaptor.capture());

        User actualUser = userArgumentCaptor.getValue();
        UserRequest actualSub = requestArgumentCaptor.getValue();

        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
        assertThat(actualSub).isEqualTo(expectedSub);
    }

    @Test
    void shouldReturn200WhenGetSubscriptions() throws Exception {
        User expectedUser = entityFactory.getUser();
        SubsResponse expectedResponse = entityFactory.getSubsResponse();

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedUser);
        given(subsService.getSubscriptions(any())).willReturn(expectedResponse);

        mvc.perform(get("/subscriptions/subscriptions")
                        .principal(PRINCIPAL)
                        .header("Authorization", VALID_AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", Matchers.notNullValue()))
                .andExpect(jsonPath("$.users[0].username", Matchers.is(expectedResponse
                        .getUsers()
                        .get(0)
                        .getUsername())));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userService).findByUsername(PRINCIPAL_USERNAME);

        verify(subsService).getSubscriptions(userArgumentCaptor.capture());

        User actualUser = userArgumentCaptor.getValue();

        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @Test
    void shouldReturn200WhenGetSubscribers() throws Exception {
        User expectedUser = entityFactory.getUser();
        SubsResponse expectedResponse = entityFactory.getSubsResponse();

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedUser);
        given(subsService.getSubscribers(any())).willReturn(expectedResponse);

        mvc.perform(get("/subscriptions/subscribers")
                        .principal(PRINCIPAL)
                        .header("Authorization", VALID_AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", Matchers.notNullValue()))
                .andExpect(jsonPath("$.users[0].username", Matchers.is(expectedResponse
                        .getUsers()
                        .get(0)
                        .getUsername())));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userService).findByUsername(PRINCIPAL_USERNAME);

        verify(subsService).getSubscribers(userArgumentCaptor.capture());

        User actualUser = userArgumentCaptor.getValue();

        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @Test
    void shouldValidateUserRequest() throws Exception {
        UserRequest userRequest = UserRequest.of("");

        mvc.perform(put("/subscriptions/sub")
                        .principal(PRINCIPAL)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(Code.REQUEST_VALIDATION_ERROR))));

        mvc.perform(put("/subscriptions/unsub")
                        .principal(PRINCIPAL)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(Code.REQUEST_VALIDATION_ERROR))));
    }

    @Test
    void shouldReturnCorrectSubscribeError() throws Exception {
        CommonException expected = entityFactory.getCommonException();
        UserRequest expectedSub = entityFactory.getUserRequest();
        when(subsService.subscribe(any(), any())).thenThrow(expected);

        mvc.perform(put("/subscriptions/sub")
                        .principal(PRINCIPAL)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedSub)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(expected.getCode()))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

    @Test
    void shouldReturnCorrectUnsubscribeError() throws Exception {
        CommonException expected = entityFactory.getCommonException();
        UserRequest expectedSub = entityFactory.getUserRequest();
        when(subsService.unsubscribe(any(), any())).thenThrow(expected);

        mvc.perform(put("/subscriptions/unsub")
                        .principal(PRINCIPAL)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedSub)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(expected.getCode()))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

    @Test
    void shouldReturnCorrectSubscriptionsError() throws Exception {
        CommonException expected = entityFactory.getCommonException();
        when(subsService.getSubscriptions(any())).thenThrow(expected);

        mvc.perform(get("/subscriptions/subscriptions")
                        .principal(PRINCIPAL)
                        .header("Authorization", VALID_AUTH_HEADER))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(expected.getCode()))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

    @Test
    void shouldReturnCorrectSubscribersError() throws Exception {
        CommonException expected = entityFactory.getCommonException();
        when(subsService.getSubscribers(any())).thenThrow(expected);

        mvc.perform(get("/subscriptions/subscribers")
                        .principal(PRINCIPAL)
                        .header("Authorization", VALID_AUTH_HEADER))
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(expected.getCode()))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

}
