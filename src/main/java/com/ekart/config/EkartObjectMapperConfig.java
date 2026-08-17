package com.ekart.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

@Configuration
public class EkartObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(
                LocalDate.class,
                new LocalDateSerializer(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return JsonMapper.builder()
                .addModule(module)
                .changeDefaultPropertyInclusion(
                        value -> value.withValueInclusion(
                                JsonInclude.Include.NON_NULL))
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .build();
    }
}
