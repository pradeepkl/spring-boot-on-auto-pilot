package com.ekart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.ekart.dev.DataSeeder;
import com.ekart.repository.OrderRepository;

@SpringBootTest(properties = "ekart.dev.seeder.enabled=false")
class SeederDisabledTests {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void dataSeederIsNotRegisteredWhenDisabled() {
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> context.getBean(DataSeeder.class));
        assertThat(orderRepository.count()).isZero();
    }
}
