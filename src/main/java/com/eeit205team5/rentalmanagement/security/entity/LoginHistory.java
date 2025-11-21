package com.eeit205team5.rentalmanagement.security.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "login_history")
@Getter
@Setter
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    private Long loginId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "user_agent", nullable = false)
    private String userAgent; // 瀏覽器user agent

    @Column(name = "login_status", nullable = false)
    private String loginStatus; // success、failed、blocked

    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}