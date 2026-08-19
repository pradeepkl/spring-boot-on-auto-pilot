package com.ekart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.ekart.controller.OrderRestController;
import com.ekart.exception.GlobalExceptionHandler;
import com.ekart.exception.OrderNotFoundException;
import com.ekart.model.CreateOrderRequest;
import com.ekart.model.ErrorResponse;
import com.ekart.model.LineItem;
import com.ekart.model.Order;
import com.ekart.model.Role;
import com.ekart.model.UpdateOrderRequest;
import com.ekart.model.UserAccount;
import com.ekart.repository.UserRepository;
import com.ekart.service.OrderService;

class OrderRestControllerTests {

    private static final String CUSTOMER_USERNAME = "customer@ekart.com";

    private OrderRestController controller;
    private FakeOrderService orderService;
    private UserRepository userRepository;
    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        orderService = new FakeOrderService();
        userRepository = proxyUserRepository();

        controller = new OrderRestController(orderService, userRepository);
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void getAllOrdersReturnsSeededData() {
        List<Order> orders = controller.getAllOrders();

        assertEquals(10, orders.size());
        assertTrue(orders.getFirst().getLineItems().isEmpty());
        assertTrue(orders.getFirst().getOwner().getUsername().startsWith("customer"));
    }

    @Test
    void getMissingOrderReturns404ErrorResponse() {
        OrderNotFoundException ex = assertThrows(
                OrderNotFoundException.class,
                () -> controller.getOrderById(999L));

        MockHttpServletRequest request = new MockHttpServletRequest(
                "GET",
                "/v1/orders/999");
        var response = exceptionHandler.handleOrderNotFound(ex, request);
        ErrorResponse body = response.getBody();

        assertEquals(404, response.getStatusCode().value());
        assertEquals("Order not found with id: 999", body.message());
        assertEquals("/v1/orders/999", body.path());
    }

    @Test
    void createUpdateAddLineItemAndDeleteOrder() {
        UserDetails customer = User.withUsername(CUSTOMER_USERNAME)
                .password("password123")
                .authorities("ROLE_CUSTOMER")
                .build();

        Order created = controller.createOrder(
                customer,
                new CreateOrderRequest(
                        "Priya Sharma",
                        "priya@example.com",
                        LocalDate.of(2025, 1, 15)));

        assertEquals(11L, created.getId());
        assertEquals("Priya Sharma", created.getCustomerName());
        assertEquals("priya@example.com", created.getEmail());

        Order withLineItem = controller.addLineItem(
                created.getId(),
                LineItem.builder()
                        .productName("Wireless Keyboard")
                        .quantity(2)
                        .price(BigDecimal.valueOf(1299.00))
                        .build());

        assertEquals(1, withLineItem.getLineItems().size());
        assertEquals("Wireless Keyboard", withLineItem.getLineItems().getFirst().getProductName());

        Order updated = controller.updateOrder(
                created.getId(),
                new UpdateOrderRequest(
                        "Priya R. Sharma",
                        "priya.sharma@example.com"));

        assertEquals("Priya R. Sharma", updated.getCustomerName());
        assertEquals("priya.sharma@example.com", updated.getEmail());

        controller.deleteOrder(created.getId());
        assertThrows(OrderNotFoundException.class,
                () -> orderService.getOrderById(created.getId()));
    }

    private static UserAccount customerAccount() {
        return UserAccount.builder()
                .id(1L)
                .username(CUSTOMER_USERNAME)
                .password("password123")
                .role(Role.CUSTOMER)
                .build();
    }

    private static UserRepository proxyUserRepository() {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                if (method.getName().equals("findByUsername")
                        && args != null
                        && args.length == 1
                        && CUSTOMER_USERNAME.equals(args[0])) {
                    return Optional.of(customerAccount());
                }
                if (method.getDeclaringClass() == Object.class) {
                    return switch (method.getName()) {
                        case "toString" -> "ProxyUserRepository";
                        case "hashCode" -> System.identityHashCode(proxy);
                        case "equals" -> proxy == args[0];
                        default -> null;
                    };
                }
                throw new UnsupportedOperationException(
                        "Unexpected repository method: " + method.getName());
            }
        };
        return (UserRepository) Proxy.newProxyInstance(
                UserRepository.class.getClassLoader(),
                new Class<?>[] {UserRepository.class},
                handler);
    }

    private static Order order(
            long id,
            String customerName,
            String email,
            UserAccount owner) {
        return Order.builder()
                .id(id)
                .customerName(customerName)
                .email(email)
                .orderDate(LocalDate.of(2025, 1, 15))
                .totalPrice(BigDecimal.valueOf(1999.00))
                .owner(owner)
                .lineItems(new ArrayList<>())
                .build();
    }

    private static final class FakeOrderService extends OrderService {

        private final Map<Long, Order> orders = new LinkedHashMap<>();
        private long nextId = 11L;

        private FakeOrderService() {
            super(null);
            UserAccount owner = customerAccount();
            for (long id = 1; id <= 10; id++) {
                orders.put(id, order(
                        id,
                        "Customer " + id,
                        "customer" + id + "@example.com",
                        owner));
            }
        }

        @Override
        public List<Order> getAllOrders() {
            return new ArrayList<>(orders.values());
        }

        @Override
        public Order getOrderById(Long id) {
            Order order = orders.get(id);
            if (order == null) {
                throw new OrderNotFoundException(id);
            }
            return order;
        }

        @Override
        public Order saveOrder(Order order) {
            order.setId(nextId++);
            orders.put(order.getId(), order);
            return order;
        }

        @Override
        public Order updateOrder(Long id, Order updatedOrder) {
            Order existing = getOrderById(id);
            existing.setCustomerName(updatedOrder.getCustomerName());
            existing.setEmail(updatedOrder.getEmail());
            return existing;
        }

        @Override
        public void deleteOrder(Long id) {
            orders.remove(id);
        }

        @Override
        public Order addLineItemToOrder(Long orderId, LineItem lineItem) {
            Order order = getOrderById(orderId);
            lineItem.setOrder(order);
            order.getLineItems().add(lineItem);
            return order;
        }
    }
}
