package ru.bolodurin.socialmedia.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import ru.bolodurin.socialmedia.model.dto.LoginRequest;
import ru.bolodurin.socialmedia.model.dto.RegisterRequest;
import ru.bolodurin.socialmedia.model.entities.Code;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.security.JwtService;
import ru.bolodurin.socialmedia.services.AuthenticationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthControllerImpl.class)
@MockBeans(value = @MockBean(JwtService.class))
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AuthControllerImplTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthenticationService authService;

    private CommonException commonException;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void init() {
        commonException = CommonException
                .builder()
                .code(Code.AUTHENTICATION_ERROR)
                .message("message")
                .build();

        registerRequest = RegisterRequest
                .builder()
                .username("user")
                .email("email@mail.com")
                .password("password")
                .build();

        loginRequest = LoginRequest
                .builder()
                .username("user")
                .password("password")
                .build();
    }

    @Test
    void shouldReturn200WhenRegistered() throws Exception {
        RegisterRequest expectedRequest = registerRequest;

        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedRequest)))
                .andExpect(status().isOk());

        ArgumentCaptor<RegisterRequest> requestArgumentCaptor = ArgumentCaptor.forClass(RegisterRequest.class);

        verify(authService).register(requestArgumentCaptor.capture());

        RegisterRequest actualRequest = requestArgumentCaptor.getValue();

        assertThat(actualRequest).usingRecursiveComparison().isEqualTo(expectedRequest);
    }

    @Test
    void shouldReturn200WhenAuthenticated() throws Exception {
        LoginRequest expectedRequest = loginRequest;

        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(expectedRequest)))
                .andExpect(status().isOk());

        ArgumentCaptor<LoginRequest> requestArgumentCaptor = ArgumentCaptor.forClass(LoginRequest.class);

        verify(authService).auth(requestArgumentCaptor.capture());

        LoginRequest actualRequest = requestArgumentCaptor.getValue();

        assertThat(actualRequest).usingRecursiveComparison().isEqualTo(expectedRequest);
    }

    @ParameterizedTest
    @CsvSource({
            " ,email@mail.com,password",
            "user,email,password",
            "user, ,password",
            "user,email@mail.com,pass",
            "user,email@mail.com, "})
    void shouldValidateRegisterRequest(String username, String email, String password) throws Exception {
        RegisterRequest registerRequest = RegisterRequest
                .builder()
                .username(username)
                .email(email)
                .password(password)
                .build();

        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(registerRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource({" ,password", "user, "})
    void shouldValidateLoginRequest(String username, String password) throws Exception {
        LoginRequest loginRequest = LoginRequest
                .builder()
                .username(username)
                .password(password)
                .build();

        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(Code.REQUEST_VALIDATION_ERROR))));
    }

    @Test
    void shouldReturnCorrectRegError() throws Exception {
        CommonException expected = commonException;
        when(authService.register(any())).thenThrow(expected);

        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(registerRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(expected.getCode()))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

    @Test
    void shouldReturnCorrectAuthError() throws Exception {
        AuthenticationException expected = new BadCredentialsException("message");
        when(authService.auth(any())).thenThrow(expected);

        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(registerRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(String.valueOf(Code.AUTHENTICATION_ERROR))))
                .andExpect(jsonPath("$.message", Matchers.is(expected.getMessage())));
    }

}
