package com.project.poblog.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info apiInfo = new Info()
            .title("poblog API")
            .description("poblog API 명세서 입니다.")
            .version("1.0.0");
        return new OpenAPI()
            .info(apiInfo);
    }

}
