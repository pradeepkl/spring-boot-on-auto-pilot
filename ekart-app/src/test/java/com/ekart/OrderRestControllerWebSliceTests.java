package com.ekart;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ekart.config.SecurityConfig;
import com.ekart.controller.OrderRestController;
import com.ekart.exception.GlobalExceptionHandler;
import com.ekart.exception.OrderNotFoundException;
import com.ekart.model.Order;
import com.ekart.repository.UserRepository;
import com.ekart.service.AppUserDetailsService;
import com.ekart.service.OrderService;

@WebMvcTest(OrderRestController.class)
@Import({GlobalExceptionHandler.class, SecurityConfig.class})
@TestPropertySource(properties = {
        "spring.profiles.active=slice",
        "ekart.security.require-auth=true"
})
class OrderRestControllerWebSliceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private AppUserDetailsService appUserDetailsService;

    @Test
    void anonymousListIsUnauthorized() throws Exception {
        mockMvc.perform(get("/v1/orders"))
                .andExpect(status().isUnauthorized());
        verifyNoInteractions(orderService);
    }

    @Test
    void customerListIsForbiddenAtHttpLayer() throws Exception {
        mockMvc.perform(get("/v1/orders")
                        .with(user("customer@ekart.com").roles("CUSTOMER")))
                .andExpect(status().isForbidden());
        verifyNoInteractions(orderService);
    }

    @Test
    void adminListReturnsMockedOrders() throws Exception {
        Order order = Order.builder()
                .id(1L)
                .customerName("Alice")
                .email("alice@example.com")
                .totalPrice(new BigDecimal("199.99"))
                .orderDate(LocalDate.of(2024, 1, 10))
                .build();
        when(orderService.getAllOrders()).thenReturn(List.of(order));

        mockMvc.perform(get("/v1/orders")
                        .with(user("admin@ekart.com").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].customerName").value("Alice"));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void deleteReturnsNoContent() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/v1/orders/1")
                        .with(user("customer@ekart.com").roles("CUSTOMER")))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(1L);
    }

    @Test
    void missingOrderReturns404() throws Exception {
        when(orderService.getOrderById(99L))
                .thenThrow(new OrderNotFoundException(99L));

        mockMvc.perform(get("/v1/orders/99")
                        .with(user("admin@ekart.com").roles("ADMIN")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Order not found with id: 99"));

        verify(orderService, times(1)).getOrderById(99L);
    }
}
