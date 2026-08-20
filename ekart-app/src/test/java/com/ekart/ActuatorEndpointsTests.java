package com.ekart;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ActuatorEndpointsTests {

    private static final String CUSTOMER = "customer@ekart.com";
    private static final String PASSWORD = "password123";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void exposesConfiguredDiagnosticEndpointsAndSeededOrdersHealth() throws Exception {
        mockMvc.perform(get("/actuator"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.health").exists())
                .andExpect(jsonPath("$._links.conditions").exists())
                .andExpect(jsonPath("$._links.beans").exists())
                .andExpect(jsonPath("$._links.env").exists())
                .andExpect(jsonPath("$._links.mappings").exists())
                .andExpect(jsonPath("$._links.startup").exists())
                .andExpect(jsonPath("$._links.metrics").exists())
                .andExpect(jsonPath("$._links.loggers").exists())
                .andExpect(jsonPath("$._links.shutdown").doesNotExist());

        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.components.seededOrders.status").value("UP"))
                .andExpect(jsonPath("$.components.seededOrders.details.orderCount")
                        .value(greaterThanOrEqualTo(10)));
    }

    @Test
    void publishesLivenessAndReadinessTransitionsInTheDevProfile() throws Exception {
        mockMvc.perform(post("/v1/state/liveness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.liveness").value("BROKEN"));
        mockMvc.perform(get("/actuator/health/liveness"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value("DOWN"));
        mockMvc.perform(post("/v1/state/liveness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.liveness").value("CORRECT"));
        mockMvc.perform(get("/actuator/health/liveness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));

        mockMvc.perform(post("/v1/state/readiness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.readiness").value("REFUSING_TRAFFIC"));
        mockMvc.perform(get("/actuator/health/readiness"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value("OUT_OF_SERVICE"));
        mockMvc.perform(post("/v1/state/readiness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.readiness").value("ACCEPTING_TRAFFIC"));
        mockMvc.perform(get("/actuator/health/readiness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void incrementsOrdersCreatedAfterAuthenticatedCreate() throws Exception {
        mockMvc.perform(post("/v1/orders")
                        .with(httpBasic(CUSTOMER, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Actuator Metric",
                                  "email": "actuator.metric@example.com",
                                  "orderDate": "2025-01-15"
                                }
                                """))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/actuator/metrics/orders.created"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("orders.created"))
                .andExpect(jsonPath("$.measurements[?(@.statistic == 'COUNT')].value",
                        hasItem(greaterThanOrEqualTo(1.0))));
    }

    @Test
    void inspectsAndChangesOrderServiceLoggerLevel() throws Exception {
        mockMvc.perform(get("/actuator/loggers/com.ekart.service.OrderService"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.effectiveLevel").exists());

        mockMvc.perform(post("/actuator/loggers/com.ekart.service.OrderService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"configuredLevel\":\"DEBUG\"}"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/actuator/loggers/com.ekart.service.OrderService"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.configuredLevel").value("DEBUG"));

        mockMvc.perform(post("/actuator/loggers/com.ekart.service.OrderService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"configuredLevel\":null}"))
                .andExpect(status().isNoContent());
    }
}
