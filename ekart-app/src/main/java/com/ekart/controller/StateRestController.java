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

@Profile("dev")
@RestController
@RequestMapping("/v1/state")
public class StateRestController {

    private final ApplicationAvailability applicationAvailability;
    private final ApplicationEventPublisher applicationEventPublisher;

    public StateRestController(
            ApplicationAvailability applicationAvailability,
            ApplicationEventPublisher applicationEventPublisher) {
        this.applicationAvailability = applicationAvailability;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostMapping("/liveness")
    public Map<String, Object> toggleLiveness() {
        LivenessState updated = applicationAvailability.getLivenessState() == LivenessState.CORRECT
                ? LivenessState.BROKEN : LivenessState.CORRECT;
        AvailabilityChangeEvent.publish(applicationEventPublisher, this, updated);
        return stateResponse("liveness", updated);
    }

    @PostMapping("/readiness")
    public Map<String, Object> toggleReadiness() {
        ReadinessState updated = applicationAvailability.getReadinessState() == ReadinessState.ACCEPTING_TRAFFIC
                ? ReadinessState.REFUSING_TRAFFIC : ReadinessState.ACCEPTING_TRAFFIC;
        AvailabilityChangeEvent.publish(applicationEventPublisher, this, updated);
        return stateResponse("readiness", updated);
    }

    private Map<String, Object> stateResponse(String name, Object state) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(name, state);
        return response;
    }
}
