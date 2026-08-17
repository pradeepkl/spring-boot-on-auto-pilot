package com.ekart.config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ekart.model.LineItem;
import com.ekart.model.Order;
import com.ekart.repository.OrderRepository;
import com.github.javafaker.Faker;

@Component
public class BootstrapAppData {

    private static final Logger log =
            LoggerFactory.getLogger(BootstrapAppData.class);

    private final OrderRepository orderRepository;
    private final int orderCount;

    public BootstrapAppData(
            OrderRepository orderRepository,
            @Value("${app.seed.order-count}") int orderCount) {
        this.orderRepository = orderRepository;
        this.orderCount = orderCount;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void seedData() {
        Faker faker = new Faker();

        log.info("Seeding {} orders...", orderCount);

        IntStream.range(0, orderCount).forEach(i -> {

            int itemCount = 2 + (int) (Math.random() * 5);

            List<LineItem> lineItems = IntStream.range(0, itemCount)
                .mapToObj(j -> {
                    int quantity = 1 + (int) (Math.random() * 5);
                    BigDecimal price = BigDecimal.valueOf(
                        10 + (Math.random() * 990)
                    ).setScale(2, RoundingMode.HALF_UP);

                    return LineItem.builder()
                        .productName(faker.commerce().productName())
                        .quantity(quantity)
                        .price(price)
                        .build();
                })
                .collect(Collectors.toList());

            BigDecimal totalPrice = lineItems.stream()
                .map(item -> item.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            Order order = Order.builder()
                .customerName(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .totalPrice(totalPrice)
                .orderDate(LocalDate.now())
                .lineItems(lineItems)
                .build();

            lineItems.forEach(item -> item.setOrder(order));

            orderRepository.save(order);
        });

        log.info("Seeding complete. {} orders inserted.", orderCount);
    }
}
