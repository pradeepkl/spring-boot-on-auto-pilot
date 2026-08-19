package com.ekart.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;

import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

@Configuration
public class EkartJacksonCustomizer
        implements JsonMapperBuilderCustomizer {

    @Override
    public void customize(JsonMapper.Builder builder) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(
                LocalDate.class,
                new LocalDateSerializer(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        builder.addModule(module);
    }
}
