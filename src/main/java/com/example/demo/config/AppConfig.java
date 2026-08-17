package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class AppConfig implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(AppConfig.class);

    private final ApplicationContext context;

    public AppConfig(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) {
        log.info("Beans registered in the application context:");
        Arrays.stream(context.getBeanDefinitionNames())
              .sorted()
              .forEach(name -> log.info("  {}", name));
    }

}
