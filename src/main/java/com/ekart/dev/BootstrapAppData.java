package com.ekart.dev;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

public class BootstrapAppData {

    private final DataSeeder dataSeeder;

    public BootstrapAppData(DataSeeder dataSeeder) {
        this.dataSeeder = dataSeeder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        dataSeeder.seed();
    }
}
