package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import com.example.demo.config.StudentConfig;

class StudentConditionTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(StudentConfig.class);

    @Test
    void whenLoadStudentFalse_fallbackRegisters() {
        contextRunner
                .withPropertyValues("loadStudent=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean("student");
                    assertThat(context).doesNotHaveBean("studentBeanCondition");
                    assertThat(context).doesNotHaveBean("studentWithClassCheck");
                    assertThat(context).hasBean("studentFallback");
                    assertThat(context).hasBean("studentConfig");
                });
    }

    @Test
    void whenLoadStudentTrue_primaryAndDependentsRegister() {
        contextRunner
                .withPropertyValues("loadStudent=true")
                .run(context -> {
                    assertThat(context).hasBean("student");
                    assertThat(context).hasBean("studentBeanCondition");
                    assertThat(context).hasBean("studentWithClassCheck");
                    assertThat(context).doesNotHaveBean("studentFallback");
                    assertThat(context).hasBean("studentConfig");
                });
    }
}
