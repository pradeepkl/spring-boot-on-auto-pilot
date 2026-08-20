package com.ekart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;

import com.ekart.config.EkartDevDataProvider;
import com.ekart.dev.BootstrapAppData;
import com.ekart.dev.SeederProperties;

@SpringBootTest(args = "--ekart.dev.seeder.order-count=3")
class ConfigurationProfilesTests {

    @Autowired
    private Environment environment;

    @Autowired
    private SeederProperties seederProperties;

    @Autowired
    private ApplicationContext context;

    @Test
    void commandLineOrderCountOverridesDevYaml() {
        assertThat(environment.matchesProfiles("dev")).isTrue();
        assertThat(environment.getProperty("ekart.dev.seeder.order-count"))
                .isEqualTo("3");
        assertThat(seederProperties.getOrderCount()).isEqualTo(3);
        assertThat(context.getBeanNamesForType(BootstrapAppData.class))
                .contains("bootstrapAppData");
        assertThat(context.containsBean("devPermitAllFilterChain")).isTrue();
        assertThat(context.containsBean("productionFilterChain")).isFalse();
    }

    @Test
    void orderCountZeroFailsWithCommandLineOrigin() {
        assertThatThrownBy(() -> new SpringApplicationBuilder(EkartApplication.class)
                .web(WebApplicationType.NONE)
                .run(
                        "--spring.main.web-application-type=none",
                        "--ekart.dev.seeder.order-count=0"))
                .satisfies(thrown -> {
                    String chain = thrown.toString() + '\n' + getCauseChain(thrown);
                    assertThat(chain).contains("ekart.dev.seeder.order-count");
                    assertThat(chain).contains("Failed to bind properties");
                    assertThat(chain).contains("commandLineArgs");
                });
    }

    private static String getCauseChain(Throwable thrown) {
        StringBuilder chain = new StringBuilder();
        Throwable current = thrown;
        while (current != null) {
            chain.append(current.getMessage()).append('\n');
            current = current.getCause();
        }
        return chain.toString();
    }
}

@SpringBootTest(properties = {
        "spring.profiles.active=prod",
        "spring.datasource.url=jdbc:h2:mem:ekartprodtest",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class ProdProfileTests {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Environment environment;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void prodSelectsAuthenticatedChainAndOmitsDevBeans() {
        assertThat(environment.getActiveProfiles()).contains("prod");
        assertThat(context.containsBean("productionFilterChain")).isTrue();
        assertThat(context.containsBean("devPermitAllFilterChain")).isFalse();
        assertThat(context.getBeanNamesForType(BootstrapAppData.class)).isEmpty();
        assertThat(context.getBeanNamesForType(EkartDevDataProvider.class)).isEmpty();
    }

    @Test
    void unauthenticatedListReturns401AtHttpLayer() throws Exception {
        mockMvc.perform(get("/v1/orders"))
                .andExpect(status().isUnauthorized());
    }
}
