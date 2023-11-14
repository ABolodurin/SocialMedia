package ru.bolodurin.socialmedia.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public static final String AUTH_TAG = "User authorization resource";
    public static final String FEED_TAG = "User feed resource";
    public static final String USER_POSTS_TAG = "User posts resource";
    public static final String SUBS_TAG = "User subscriptions resource";
    public static final String MESSENGER_TAG = "Messenger resource";

    @Value(value = "${application.swagger.info.title}")
    private String title;
    @Value(value = "${application.swagger.info.description}")
    private String description;
    @Value(value = "${application.swagger.info.version}")
    private String version;
    @Value(value = "${application.swagger.info.contact.name}")
    private String name;
    @Value(value = "${application.swagger.info.contact.email}")
    private String email;
    @Value(value = "${application.swagger.info.contact.url}")
    private String url;


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version)
                        .contact(new Contact()
                                .name(name)
                                .email(email)
                                .url(url)));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

}
