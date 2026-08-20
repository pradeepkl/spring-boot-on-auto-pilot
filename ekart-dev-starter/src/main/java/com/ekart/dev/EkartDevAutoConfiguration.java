package com.ekart.dev;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = DataJpaRepositoriesAutoConfiguration.class)
@EnableConfigurationProperties(SeederProperties.class)
public class EkartDevAutoConfiguration {

    @Bean
    @ConditionalOnProperty(
            prefix = "ekart.dev.seeder",
            name = "enabled",
            havingValue = "true",
            matchIfMissing = true)
    @ConditionalOnBean(DevDataProvider.class)
    public DataSeeder dataSeeder(
            DevDataProvider devDataProvider,
            SeederProperties properties) {
        return new DataSeeder(devDataProvider, properties);
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "ekart.dev.seeder",
            name = "enabled",
            havingValue = "true",
            matchIfMissing = true)
    @ConditionalOnBean(DataSeeder.class)
    public BootstrapAppData bootstrapAppData(DataSeeder dataSeeder) {
        return new BootstrapAppData(dataSeeder);
    }
}
