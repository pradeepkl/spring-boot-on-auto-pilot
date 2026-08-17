package com.ekart.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.model.CreateOrderRequest;
import com.ekart.model.LineItem;
import com.ekart.model.Order;
import com.ekart.model.UpdateOrderRequest;
import com.ekart.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;
    private static final Logger log =
            LoggerFactory.getLogger(OrderRestController.class);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders() {
        log.info("Fetching all orders");
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderById(@PathVariable Long id) {
        log.info("Fetching order with id: {}", id);
        return orderService.getOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(
            @Valid @RequestBody CreateOrderRequest request) {
        log.info("Creating order for customer: {}",
                request.customerName());
        Order order = Order.builder()
            .customerName(request.customerName())
            .email(request.email())
            .orderDate(request.orderDate())
            .totalPrice(BigDecimal.ZERO)
            .build();
        return orderService.saveOrder(order);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Order updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderRequest request) {
        log.info("Updating order with id: {}", id);
        Order order = Order.builder()
            .customerName(request.customerName())
            .email(request.email())
            .build();
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        log.info("Deleting order with id: {}", id);
        orderService.deleteOrder(id);
    }

    @PostMapping("/{id}/lineitems")
    @ResponseStatus(HttpStatus.CREATED)
    public Order addLineItem(
            @PathVariable Long id,
            @RequestBody LineItem lineItem) {
        log.info("Adding line item to order with id: {}", id);
        return orderService.addLineItemToOrder(id, lineItem);
    }
}
