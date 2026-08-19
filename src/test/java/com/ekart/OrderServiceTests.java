package com.ekart;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ekart.model.Order;
import com.ekart.repository.OrderRepository;
import com.ekart.service.OrderService;

@SpringBootTest
class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void seededOrdersAreAvailable() {
        assertThat(orderService.getAllOrders()).hasSize(10);
        Order first = orderService.getOrderById(1L);
        assertThat(first.getCustomerName()).isNotBlank();
        assertThat(first.getEmail()).isNotBlank();
        assertThat(first.getTotalPrice()).isNotNull();
    }

    @Test
    void repositoriesAreRegistered() {
        assertThat(orderRepository.count()).isEqualTo(10);
    }
}
