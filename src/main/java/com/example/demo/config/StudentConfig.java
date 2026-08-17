package com.example.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Student;

@Configuration
public class StudentConfig {

    @Bean
    @ConditionalOnProperty(
            name = "loadStudent",
            havingValue = "true",
            matchIfMissing = false)
    public Student student() {
        return new Student("baseline");
    }

    @Bean
    @ConditionalOnBean(Student.class)
    public Student studentBeanCondition() {
        return new Student("bean-condition");
    }

    @Bean
    @ConditionalOnMissingBean(Student.class)
    public Student studentFallback() {
        return new Student("fallback");
    }

    @Bean
    @ConditionalOnProperty(name = "loadStudent", havingValue = "true")
    @ConditionalOnClass(name = "com.example.demo.model.Student")
    public Student studentWithClassCheck() {
        return new Student("class-checked");
    }
}
