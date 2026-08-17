package com.ekart.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.ekart.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Component("orderSecurity")
@RequiredArgsConstructor
public class OrderSecurity {
    private final OrderRepository orderRepository;
    public boolean isOwner(Authentication authentication, Long orderId) {
        return orderRepository.findById(orderId)
            .map(order -> order.getOwner().getUsername().equals(authentication.getName()))
            .orElse(false);
    }
}
