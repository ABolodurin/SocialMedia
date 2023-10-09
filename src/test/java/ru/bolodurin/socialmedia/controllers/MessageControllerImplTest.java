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
import ru.bolodurin.socialmedia.model.dto.MessageRequest;
import ru.bolodurin.socialmedia.model.dto.MessageResponse;
import ru.bolodurin.socialmedia.model.dto.UserResponse;
import ru.bolodurin.socialmedia.model.entities.Code;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.security.JwtService;
import ru.bolodurin.socialmedia.services.MessageService;
import ru.bolodurin.socialmedia.services.MessageServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageControllerImpl.class)
@MockBeans(value = @MockBean(JwtService.class))
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MessageControllerImplTest {
    private static final String PRINCIPAL_USERNAME = "username";
    private static final String VALID_AUTH_HEADER = "validHeader";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MessageService messageService;
    @MockBean
    private UserService userService;

    private Principal principal;
    private User user1;
    private User user2;
    private MessageRequest messageRequest;
    private MessageResponse messageResponse;
    private CommonException commonException;

    @BeforeEach
    void init() {
        principal = new UserPrincipal(PRINCIPAL_USERNAME);

        user1 = User
                .builder()
                .username(PRINCIPAL_USERNAME)
                .build();

        user2 = User
                .builder()
                .username("user2")
                .build();

        messageRequest = MessageRequest
                .builder()
                .consumer(user2.getUsername())
                .message("message")
                .build();

        messageResponse = MessageResponse
                .builder()
                .from(user1.getUsername())
                .to(user2.getUsername())
                .message(messageRequest.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        commonException = CommonException
                .builder()
                .code(Code.FEED_IS_EMPTY)
                .message("message")
                .build();
    }

    @Test
    void shouldReturn200WhenMessageIsSent() throws Exception {
        User expectedUser = user1;
        MessageRequest expectedMessage = messageRequest;
        MessageResponse expectedResponse = messageResponse;

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedUser);
        given(messageService.sendMessage(any(), any())).willReturn(new PageImpl<>(
                List.of(expectedResponse), MessageServiceImpl.DEFAULT_PAGEABLE, 1));


        mvc.perform(post("/messenger")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedMessage)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.notNullValue()))
                .andExpect(jsonPath("$.pageable", Matchers.notNullValue()))
                .andExpect(jsonPath("$.content[0].from", Matchers.is(expectedResponse.getFrom())))
                .andExpect(jsonPath("$.content[0].to", Matchers.is(expectedResponse.getTo())))
                .andExpect(jsonPath("$.content[0].message", Matchers.is(expectedResponse.getMessage())))
                .andExpect(jsonPath("$.content[0].timestamp",
                        Matchers.startsWith(expectedResponse.getTimestamp()
                                .toString()
                                .substring(0, 25))));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<MessageRequest> messageArgumentCaptor = ArgumentCaptor.forClass(MessageRequest.class);

        verify(userService).findByUsername(PRINCIPAL_USERNAME);

        verify(messageService).sendMessage(userArgumentCaptor.capture(), messageArgumentCaptor.capture());

        User actualUser = userArgumentCaptor.getValue();
        MessageRequest actualMessage = messageArgumentCaptor.getValue();

        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturn200WhenChatIsShown() throws Exception {
        User expectedProducer = user1;
        UserResponse expectedConsumer = UserResponse.of(user2.getUsername());
        MessageResponse expectedResponse = messageResponse;

        given(userService.findByUsername(PRINCIPAL_USERNAME)).willReturn(expectedProducer);
        given(messageService.getChatWith(any(), any())).willReturn(new PageImpl<>(
                List.of(expectedResponse), MessageServiceImpl.DEFAULT_PAGEABLE, 1));


        mvc.perform(get("/messenger")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedConsumer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.notNullValue()))
                .andExpect(jsonPath("$.pageable", Matchers.notNullValue()))
                .andExpect(jsonPath("$.content[0].from", Matchers.is(expectedResponse.getFrom())))
                .andExpect(jsonPath("$.content[0].to", Matchers.is(expectedResponse.getTo())))
                .andExpect(jsonPath("$.content[0].message", Matchers.is(expectedResponse.getMessage())))
                .andExpect(jsonPath("$.content[0].timestamp",
                        Matchers.startsWith(expectedResponse.getTimestamp()
                                .toString()
                                .substring(0, 25))));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<UserResponse> responseArgumentCaptor = ArgumentCaptor.forClass(UserResponse.class);

        verify(userService).findByUsername(PRINCIPAL_USERNAME);

        verify(messageService).getChatWith(responseArgumentCaptor.capture(), userArgumentCaptor.capture());

        User actualProducer = userArgumentCaptor.getValue();
        UserResponse actualConsumer = responseArgumentCaptor.getValue();

        assertThat(actualProducer).usingRecursiveComparison().isEqualTo(expectedProducer);
        assertThat(actualConsumer).usingRecursiveComparison().isEqualTo(expectedConsumer);
    }

    @ParameterizedTest
    @CsvSource({" ,message", "consumer, "})
    void shouldValidateMessageRequest(String consumer, String message) throws Exception {
        MessageRequest expectedMessage = MessageRequest
                .builder()
                .consumer(consumer)
                .message(message)
                .build();

        mvc.perform(post("/messenger")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedMessage)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(Code.REQUEST_VALIDATION_ERROR))));
    }

    @Test
    void shouldValidateUserRequest() throws Exception {
        UserResponse expectedConsumer = UserResponse.of("");

        mvc.perform(get("/messenger")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedConsumer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(Code.REQUEST_VALIDATION_ERROR))));
    }

    @Test
    void shouldReturnCorrectMessageError() throws Exception {
        CommonException expected = commonException;
        when(messageService.sendMessage(any(), any())).thenThrow(expected);

        mvc.perform(post("/messenger")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(messageRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(expected.getCode()))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

    @Test
    void shouldReturnCorrectChatError() throws Exception {
        CommonException expected = commonException;
        when(messageService.getChatWith(any(), any())).thenThrow(expected);

        mvc.perform(get("/messenger")
                        .principal(principal)
                        .header("Authorization", VALID_AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(UserResponse.of(user2.getUsername()))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(expected.getCode()))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

}
