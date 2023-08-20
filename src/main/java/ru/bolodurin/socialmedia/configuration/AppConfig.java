package ru.bolodurin.socialmedia.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.bolodurin.socialmedia.services.UserService;

@Configuration
//@OpenAPIDefinition(info = @Info(title = "Social Media Pet Project"))
@ComponentScan("ru.bolodurin.socialmedia")
@RequiredArgsConstructor
public class AppConfig {
    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

}
