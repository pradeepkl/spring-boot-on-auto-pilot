package com.ekart.config;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.ekart.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class SeededOrdersHealthIndicator implements HealthIndicator {

    private final OrderRepository orderRepository;

    @Override
    public Health health() {
        long orderCount = orderRepository.count();

        if (orderCount == 0) {
            return Health.down()
                    .withDetail("reason", "no orders seeded")
                    .build();
        }

        return Health.up()
                .withDetail("orderCount", orderCount)
                .build();
    }
}
