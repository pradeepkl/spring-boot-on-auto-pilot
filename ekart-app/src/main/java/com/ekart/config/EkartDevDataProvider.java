package com.ekart.config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ekart.dev.DevDataProvider;
import com.ekart.dev.SeederProperties;
import com.ekart.model.LineItem;
import com.ekart.model.Order;
import com.ekart.model.Role;
import com.ekart.model.UserAccount;
import com.ekart.repository.OrderRepository;
import com.ekart.repository.UserRepository;

import net.datafaker.Faker;

@Component
public class EkartDevDataProvider implements DevDataProvider {

    private static final Logger log =
            LoggerFactory.getLogger(EkartDevDataProvider.class);

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SeederProperties seederProperties;
    private boolean seeded;

    public EkartDevDataProvider(
            OrderRepository orderRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            SeederProperties seederProperties) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.seederProperties = seederProperties;
    }

    @Override
    public void seedData() {
        if (seeded || orderRepository.count() > 0) {
            return;
        }

        UserAccount customer = seedUser("customer@ekart.com", Role.CUSTOMER);
        seedUser("othercustomer@ekart.com", Role.CUSTOMER);
        seedUser("admin@ekart.com", Role.ADMIN);

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
                    .owner(customer)
                    .lineItems(lineItems)
                    .build();

            lineItems.forEach(item -> item.setOrder(order));
            orderRepository.save(order);
        }

        log.info("Seeded {} orders for customer: {}",
                orderCount, customer.getUsername());
        seeded = true;
    }

    private UserAccount seedUser(String username, Role role) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            UserAccount account = UserAccount.builder()
                    .username(username)
                    .password(passwordEncoder.encode("password123"))
                    .role(role)
                    .build();
            UserAccount saved = userRepository.save(account);
            log.info("Seeded user: {} with role: {}", username, role);
            return saved;
        });
    }
}
