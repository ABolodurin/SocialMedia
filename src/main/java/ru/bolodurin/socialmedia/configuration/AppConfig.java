package ru.bolodurin.socialmedia.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.bolodurin.socialmedia.services.UserService;

@Configuration
@ComponentScan("ru.bolodurin.socialmedia")
@RequiredArgsConstructor
public class AppConfig {
    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::findByUsername;
    }

}
