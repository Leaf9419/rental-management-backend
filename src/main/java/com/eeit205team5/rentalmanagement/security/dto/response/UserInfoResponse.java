package com.eeit205team5.rentalmanagement.security.dto.response;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
    private Long userId;
    private String email;
    private String phone;
    private String role;
    private String status;
    private Boolean emailVerified;
    private Boolean phoneVerified;
    // private String provider;
    // private String providerId;
    private Instant createdAt;
    // private Instant updatedAt;
    // private Instant lastLoginAt;
}