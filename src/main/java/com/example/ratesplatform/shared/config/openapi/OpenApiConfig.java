package com.example.ratesplatform.shared.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "springdoc")
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(final @Autowired Environment env) {
        return new OpenAPI()
                .info(new Info()
                        .title(env.getProperty("spring-doc.docs.title"))
                        .description(env.getProperty("spring-doc.docs.description"))
                        .version(env.getProperty("spring-doc.docs.version"))
                        .termsOfService(env.getProperty("spring-doc.docs.terms"))
                        .contact(new Contact().name("Virtual Cave"))
                        .license(new License()
                                .name("Rates API Terms and License")
                                .url("http://www.virtualcave.es/terms-of-service.html"))
                        .extensions(getApiExtensions(env)))
                .components(getGlobalSecurityScheme())
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"));
    }

    private static Map<String, Object> getApiExtensions(final Environment env) {
        Map<String, Object> extensions = new LinkedHashMap<>();
        extensions.put("x-ibm-name", env.getProperty("spring-doc.docs.title"));
        extensions.put("x-virtualcave-catalogation", Map.of(
                "bian-landscape-version", StringUtils.EMPTY,
                "bian-business-area", StringUtils.EMPTY,
                "bian-business-domain", StringUtils.EMPTY,
                "bian-service-domain", StringUtils.EMPTY
        ));
        return extensions;
    }

    private static Components getGlobalSecurityScheme() {
        return new Components()
                .addSecuritySchemes("BearerAuth",
                        new io.swagger.v3.oas.models.security.SecurityScheme()
                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer"));
    }
}
