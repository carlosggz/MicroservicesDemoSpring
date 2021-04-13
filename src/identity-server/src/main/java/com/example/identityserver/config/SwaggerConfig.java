package com.example.identityserver.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "${springdoc.swagger-ui.enabled}", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("Identity Server API")
                    .description("Example of an identity server using jwt token")
                    .version("v0.0.1")
                    .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }
}
