package com.ekart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        SpringApplication.run(EkartApplication.class, args);
    }

}
