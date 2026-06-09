package com.mcmanuel.configuration;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "catalog")
public record ApplicationConfiguration(
        @DefaultValue("10") @Min(1) int pageSize,
        String exchangeName,
        String borrowBookQueue,
        String returnedBookQueue,
        String newBookQueue) {}
