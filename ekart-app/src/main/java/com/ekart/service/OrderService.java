package com.ekart.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ekart.exception.OrderNotFoundException;
import com.ekart.model.LineItem;
import com.ekart.model.Order;
import com.ekart.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger log =
            LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public List<Order> getAllOrders() {
        log.debug("Fetching all orders");
        return orderRepository.findAll();
    }

    @PreAuthorize(
            "hasRole('ADMIN') or " +
            "(hasRole('CUSTOMER') and " +
            "@orderSecurity.isOwner(authentication, #id))")
    public Order getOrderById(Long id) {
        log.debug("Fetching order with id: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Order not found with id: {}", id);
                    return new OrderNotFoundException(id);
                });
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public Order saveOrder(Order order) {
        log.info("Saving order for customer: {}",
                order.getCustomerName());
        return orderRepository.save(order);
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER') and " +
            "@orderSecurity.isOwner(authentication, #id)")
    public Order updateOrder(Long id, Order updatedOrder) {
        log.info("Updating order with id: {}", id);
        Order existing = getOrderById(id);
        existing.setCustomerName(updatedOrder.getCustomerName());
        existing.setEmail(updatedOrder.getEmail());
        log.debug("Updated order: {}", existing);
        return orderRepository.save(existing);
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER') and " +
            "@orderSecurity.isOwner(authentication, #id)")
    public void deleteOrder(Long id) {
        log.info("Deleting order with id: {}", id);
        Order existing = getOrderById(id);
        orderRepository.delete(existing);
        log.debug("Deleted order with id: {}", id);
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER') and " +
            "@orderSecurity.isOwner(authentication, #orderId)")
    public Order addLineItemToOrder(Long orderId, LineItem lineItem) {
        log.info("Adding line item to order {}", orderId);
        Order order = getOrderById(orderId);
        lineItem.setOrder(order);
        order.getLineItems().add(lineItem);
        log.debug("LineItem added to order {}", orderId);
        return orderRepository.save(order);
    }
}
