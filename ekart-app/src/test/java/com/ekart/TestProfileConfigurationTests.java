package com.ekart;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TestProfileConfigurationTests {

    @Autowired
    private Environment environment;

    @Test
    void testProfileOverridesDatasourceToDedicatedH2() {
        assertThat(environment.getActiveProfiles()).contains("test");
        assertThat(environment.getProperty("spring.datasource.url"))
                .isEqualTo("jdbc:h2:mem:ekarttestdb");
        assertThat(environment.getProperty("ekart.dev.seeder.enabled"))
                .isEqualTo("false");
    }
}
