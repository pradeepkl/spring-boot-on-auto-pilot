package com.ekart.dev;

public interface DevDataProvider {

    /**
     * Seed development data into the consuming application's persistence layer.
     * Implementations are responsible for creating entities, saving them through
     * repositories, and managing transaction boundaries.
     */
    void seedData();
}
