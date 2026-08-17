package com.ekart.config;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.ekart.repository.OrderRepository;

/** Verifies the development fixture that this chapter uses for operational evidence. */
@Profile("dev")
@Component
public class SeededOrdersHealthIndicator implements HealthIndicator {

    private final OrderRepository orderRepository;

    public SeededOrdersHealthIndicator(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Health health() {
        long orderCount = orderRepository.count();
        if (orderCount == 0) {
            return Health.down().withDetail("reason", "no development orders seeded").build();
        }
        return Health.up().withDetail("orderCount", orderCount).build();
    }
}
