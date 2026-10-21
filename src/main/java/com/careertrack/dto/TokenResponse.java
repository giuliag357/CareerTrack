package com.careertrack.dto;

public record TokenResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}
