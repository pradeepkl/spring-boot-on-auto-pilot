package com.ekart.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final DevDataProvider devDataProvider;
    private final SeederProperties properties;

    public DataSeeder(DevDataProvider devDataProvider, SeederProperties properties) {
        this.devDataProvider = devDataProvider;
        this.properties = properties;
    }

    public void seed() {
        int count = properties.getOrderCount();
        log.info("ekart-dev-starter: seeding {} records...", count);
        for (int i = 0; i < count; i++) {
            devDataProvider.seedData();
        }
        log.info("ekart-dev-starter: seeding complete.");
    }
}
