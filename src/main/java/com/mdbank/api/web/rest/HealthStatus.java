package com.mdbank.api.web.rest;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
@Component
public class HealthStatus implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().build();
    }
}