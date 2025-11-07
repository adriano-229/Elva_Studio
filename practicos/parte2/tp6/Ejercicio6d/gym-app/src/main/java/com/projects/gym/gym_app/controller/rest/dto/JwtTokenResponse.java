package com.projects.gym.gym_app.controller.rest.dto;

public record JwtTokenResponse(String accessToken, String refreshToken, String tokenType) {
    public JwtTokenResponse(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, "Bearer");
    }
}
