package com.mcmanuel.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "user")
public record ApplicationConfiguration() {
}
