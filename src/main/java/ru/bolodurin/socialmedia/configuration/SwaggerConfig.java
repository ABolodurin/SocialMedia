package ru.bolodurin.socialmedia.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    public static final String AUTH_TAG = "User authorization resource";
    public static final String FEED_TAG = "User feed resource";
    public static final String USER_POSTS_TAG = "User posts resource";
    public static final String SUBS_TAG = "User subscriptions resource";
    public static final String MESSENGER_TAG = "Messenger resource";
    public static final String AUTH_ANNOTATION = "\"Bearer \"+ autorization token";

    private ApiInfo metaInfo() {
        return new ApiInfo(
                "Social media",
                "Social media API pet project",
                "1.0",
                null,
                new Contact("Aleksandr Bolodurin", "https://t.me/bandit26", "shur026@yandex.ru"),
                null,
                null, Collections.emptyList());
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.bolodurin.socialmedia"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
    }

}
