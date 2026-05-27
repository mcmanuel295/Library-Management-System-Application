package com.mcmanuel.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "borrow")
public record ApplicationConfiguration(
        String ExchangeName,
        String borrowQueue,
        String returnQueue
) { }
