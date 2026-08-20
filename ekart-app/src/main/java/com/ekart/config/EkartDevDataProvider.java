package com.ekart.config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.ekart.dev.DevDataProvider;
import com.ekart.dev.SeederProperties;
import com.ekart.model.LineItem;
import com.ekart.model.Order;
import com.ekart.repository.OrderRepository;

import net.datafaker.Faker;

@Component
public class EkartDevDataProvider implements DevDataProvider {

    private final OrderRepository orderRepository;
    private final SeederProperties seederProperties;
    private boolean seeded;

    public EkartDevDataProvider(
            OrderRepository orderRepository,
            SeederProperties seederProperties) {
        this.orderRepository = orderRepository;
        this.seederProperties = seederProperties;
    }

    @Override
    public void seedData() {
        if (seeded || orderRepository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        int orderCount = seederProperties.getOrderCount();

        for (int i = 0; i < orderCount; i++) {
            int itemCount = 2 + ThreadLocalRandom.current().nextInt(5);

            List<LineItem> lineItems = IntStream.range(0, itemCount)
                    .mapToObj(j -> {
                        int quantity = 1 + ThreadLocalRandom.current().nextInt(5);
                        BigDecimal price = BigDecimal.valueOf(
                                10 + (ThreadLocalRandom.current().nextDouble() * 990))
                                .setScale(2, RoundingMode.HALF_UP);

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
        }

        seeded = true;
    }
}
