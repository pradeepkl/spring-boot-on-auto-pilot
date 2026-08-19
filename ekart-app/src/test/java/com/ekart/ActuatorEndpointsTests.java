package com.ekart;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ActuatorEndpointsTests {

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
                .andExpect(jsonPath("$._links.mappings").exists());

        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.components.seededOrders").exists());
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

        mockMvc.perform(post("/v1/state/readiness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.readiness").value("REFUSING_TRAFFIC"));
        mockMvc.perform(get("/actuator/health/readiness"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value("OUT_OF_SERVICE"));
        mockMvc.perform(post("/v1/state/readiness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.readiness").value("ACCEPTING_TRAFFIC"));
    }
}
