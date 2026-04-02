package com.glitch.knowledge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${application.domain.url}")
    private String appUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/maison/verify/**")
                .allowedOrigins(this.appUrl)
                .allowedMethods("GET")
                .allowCredentials(true);
    }
}
