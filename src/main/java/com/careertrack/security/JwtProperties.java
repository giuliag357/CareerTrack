package com.careertrack.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

// Collega le proprietà "app.jwt.*" di application.properties a questo record
@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(String secret, Duration expiration) {
}
