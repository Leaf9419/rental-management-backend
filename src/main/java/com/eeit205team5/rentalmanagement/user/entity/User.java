package com.eeit205team5.rentalmanagement.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "password")
    private String password; // BCrypt加密後密碼

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "role", nullable = false)
    private String role; // tenant、landlord、admin

    @Column(name = "status", nullable = false)
    private String status; // active(啟用中)、inactive(未啟用、尚未驗證、暫時停用)、suspended(停權)

    // 如果依賴資料表有default，只有在insert時資料庫會補
    // 推薦在Entity層加入default value，避免new User()時欄位是null
    @Column(name = "email_verfied", nullable = false)
    private Boolean emailVerfied = false; // 第三方登入可以在OAuth回傳的JWT或API裡取得Email

    @Column(name = "phone_verfied", nullable = false)
    private Boolean phoneVerfied = false;

    @Column(name = "provider")
    private String provider; // 第三方登入 // google、facebook、line

    @Column(name = "provider_id")
    private String providerId; // 第三方登入唯一ID

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}