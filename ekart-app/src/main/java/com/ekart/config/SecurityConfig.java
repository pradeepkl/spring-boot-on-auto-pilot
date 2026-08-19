package com.ekart.config;

import org.springframework.context.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import com.ekart.service.AppUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AppUserDetailsService userDetailsService;
    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean
    @ConditionalOnProperty(
            prefix = "ekart.security",
            name = "require-auth",
            havingValue = "true",
            matchIfMissing = true)
    SecurityFilterChain productionFilterChain(HttpSecurity http, PasswordEncoder encoder) throws Exception {
        var provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(encoder);
        return http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(provider)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health/**").permitAll()
                .requestMatchers("/actuator/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/v1/orders").hasRole("ADMIN")
                .requestMatchers("/v1/orders/**").authenticated()
                .anyRequest().permitAll())
            .httpBasic(basic -> {})
            .build();
    }

    @Bean
    @Profile("dev")
    @ConditionalOnProperty(
            prefix = "ekart.security",
            name = "require-auth",
            havingValue = "false")
    SecurityFilterChain devPermitAllFilterChain(HttpSecurity http, PasswordEncoder encoder) throws Exception {
        var provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(encoder);
        return http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(provider)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .httpBasic(basic -> {})
            .build();
    }
}
