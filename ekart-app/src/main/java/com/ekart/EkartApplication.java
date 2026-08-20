package com.ekart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication(scanBasePackages = {
        "com.ekart.config",
        "com.ekart.controller",
        "com.ekart.exception",
        "com.ekart.repository",
        "com.ekart.security",
        "com.ekart.service"
})
public class EkartApplication {

    public static void main(String[] args) {
        SpringApplication application =
                new SpringApplication(EkartApplication.class);
        application.setApplicationStartup(
                new BufferingApplicationStartup(2048));
        application.run(args);
    }

}
