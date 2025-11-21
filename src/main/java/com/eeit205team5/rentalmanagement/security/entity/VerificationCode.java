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
@Table(name = "verification_code")
@Getter
@Setter
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long codeId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "type", nullable = false)
    private String type; // 驗證碼用途 // email、phone、reset_password、login_2fa

    @Column(name = "used")
    private Boolean used = false;

    @Column(name = "used_at")
    private Instant usedAt;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}