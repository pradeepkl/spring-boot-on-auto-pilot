package com.ekart;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.ekart.model.Order;
import com.ekart.model.Role;
import com.ekart.model.UserAccount;
import com.ekart.repository.OrderRepository;
import com.ekart.repository.UserRepository;

@DataJpaTest
class OrderRepositorySliceTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldReturnOrdersMatchingEmail() {
        UserAccount owner = userRepository.save(UserAccount.builder()
                .username("alice-owner@example.com")
                .password("{noop}unused")
                .role(Role.CUSTOMER)
                .build());

        Order alice = Order.builder()
                .customerName("Alice")
                .email("alice@example.com")
                .totalPrice(new BigDecimal("149.99"))
                .orderDate(LocalDate.of(2024, 1, 10))
                .owner(owner)
                .build();
        Order bob = Order.builder()
                .customerName("Bob")
                .email("bob@example.com")
                .totalPrice(new BigDecimal("89.50"))
                .orderDate(LocalDate.of(2024, 1, 11))
                .owner(owner)
                .build();
        orderRepository.saveAll(List.of(alice, bob));

        List<Order> results =
                orderRepository.findByEmail("alice@example.com");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getCustomerName()).isEqualTo("Alice");
        assertThat(results.get(0).getId()).isNotNull();
    }
}
