package com.ekart.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Profile("dev")
@RestController
@RequestMapping("/v1/state")
@RequiredArgsConstructor
public class StateRestController {

    private final ApplicationAvailability applicationAvailability;
    private final ApplicationEventPublisher applicationEvent;

    @PostMapping("/liveness")
    public Map<String, Object> livenessState() {
        LivenessState current = this.applicationAvailability
                .getLivenessState();
        LivenessState updated =
                current == LivenessState.CORRECT
                        ? LivenessState.BROKEN
                        : LivenessState.CORRECT;
        String state = updated == LivenessState.CORRECT
                ? "System is functioning"
                : "Application is not functioning";
        AvailabilityChangeEvent.publish(
                applicationEvent,
                state,
                updated);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("liveness", updated);
        response.put("state", state);
        return response;
    }

    @PostMapping("/readiness")
    public Map<String, Object> readinessState() {
        ReadinessState current = this.applicationAvailability
                .getReadinessState();
        ReadinessState updated =
                current == ReadinessState.ACCEPTING_TRAFFIC
                        ? ReadinessState.REFUSING_TRAFFIC
                        : ReadinessState.ACCEPTING_TRAFFIC;
        String state = updated == ReadinessState.ACCEPTING_TRAFFIC
                ? "System is accepting traffic"
                : "Application is refusing traffic";
        AvailabilityChangeEvent.publish(
                applicationEvent,
                state,
                updated);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("readiness", updated);
        response.put("state", state);
        return response;
    }
}
