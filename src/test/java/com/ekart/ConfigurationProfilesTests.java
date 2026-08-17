package com.ekart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

class ConfigurationProfilesTests {

    @Test
    void commandLinePropertyOverridesTheDevProfileAndSelectsDevBeans() {
        try (ConfigurableApplicationContext context = start(
                "--spring.profiles.active=dev",
                "--ekart.dev.seeder.order-count=3")) {
            assertThat(context.getEnvironment().getProperty("ekart.dev.seeder.order-count"))
                    .isEqualTo("3");
            assertThat(context.containsBean("bootstrapAppData")).isTrue();
            assertThat(context.containsBean("devPermitAllFilterChain")).isTrue();
            assertThat(context.containsBean("productionFilterChain")).isFalse();
        }
    }

    @Test
    void prodProfileSelectsTheAuthenticatedChainAndOmitsTheDevSeeder() {
        try (ConfigurableApplicationContext context = start(
                "--spring.profiles.active=prod",
                "--spring.datasource.url=jdbc:h2:mem:profileprod;DB_CLOSE_DELAY=-1",
                "--spring.datasource.username=sa",
                "--spring.datasource.password=",
                "--spring.datasource.driver-class-name=org.h2.Driver",
                "--spring.jpa.hibernate.ddl-auto=create-drop")) {
            assertThat(context.containsBean("bootstrapAppData")).isFalse();
            assertThat(context.containsBean("devPermitAllFilterChain")).isFalse();
            assertThat(context.containsBean("productionFilterChain")).isTrue();
        }
    }

    @Test
    void invalidSeederConfigurationFailsDuringContextRefresh() {
        assertThatThrownBy(() -> start(
                "--spring.profiles.active=dev",
                "--ekart.dev.seeder.order-count=0"))
                .hasStackTraceContaining("ekart.dev.seeder.order-count")
                .hasStackTraceContaining("must be greater than or equal to 1");
    }

    private ConfigurableApplicationContext start(String... args) {
        SpringApplication application = new SpringApplication(EkartApplication.class);
        String[] serverArgs = new String[args.length + 1];
        System.arraycopy(args, 0, serverArgs, 0, args.length);
        serverArgs[args.length] = "--server.port=0";
        return application.run(serverArgs);
    }
}
