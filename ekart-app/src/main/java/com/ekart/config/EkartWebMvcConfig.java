package com.ekart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EkartWebMvcConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v1/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .maxAge(3600);
            }
        };
    }

    @Bean
    public WebMvcConfigurer interceptorConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                // Intentionally scaffolded for future request interceptors.
            }
        };
    }

    @Bean
    public WebMvcConfigurer contentNegotiationConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void configureContentNegotiation(
                    ContentNegotiationConfigurer configurer) {
                // Intentionally scaffolded for future content negotiation rules.
            }
        };
    }
}
