package com.ekart.config;

import java.util.Map;

import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EkartHibernateCustomizer
        implements HibernatePropertiesCustomizer {

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(
                AvailableSettings.PHYSICAL_NAMING_STRATEGY,
                PhysicalNamingStrategyStandardImpl.class.getName());
    }
}
