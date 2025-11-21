package com.eeit205team5.rentalmanagement.security.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String accessToken;
    private Long userId;
    private String email;
    private String role;
    @Builder.Default
    private String tokenType = "Bearer";
}