package com.ekart;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class OrderRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllOrdersReturnsSeededData() throws Exception {
        mockMvc.perform(get("/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(10)));
    }

    @Test
    void getMissingOrderReturns404ErrorResponse() throws Exception {
        mockMvc.perform(get("/v1/orders/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Order not found with id: 999"))
                .andExpect(jsonPath("$.path").value("/v1/orders/999"));
    }

    @Test
    void createUpdateAddLineItemAndDeleteOrder() throws Exception {
        MvcResult created = mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Priya Sharma",
                                  "email": "priya@example.com",
                                  "orderDate": "2025-01-15"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.customerName").value("Priya Sharma"))
                .andExpect(jsonPath("$.orderDate").value("15-01-2025"))
                .andReturn();

        String body = created.getResponse().getContentAsString();
        String id = body.replaceAll("(?s).*\"id\"\\s*:\\s*(\\d+).*", "$1");

        mockMvc.perform(post("/v1/orders/" + id + "/lineitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productName": "Wireless Keyboard",
                                  "quantity": 2,
                                  "price": 1299.00
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.lineItems").isArray())
                .andExpect(jsonPath("$.lineItems[0].productName").value("Wireless Keyboard"));

        mockMvc.perform(put("/v1/orders/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Priya R. Sharma",
                                  "email": "priya.sharma@example.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Priya R. Sharma"))
                .andExpect(jsonPath("$.email").value("priya.sharma@example.com"));

        mockMvc.perform(delete("/v1/orders/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void corsAllowsLocalhost3000OnV1Orders() throws Exception {
        mockMvc.perform(options("/v1/orders")
                        .header("Origin", "http://localhost:3000")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string(
                        "Access-Control-Allow-Origin",
                        "http://localhost:3000"));
    }
}
