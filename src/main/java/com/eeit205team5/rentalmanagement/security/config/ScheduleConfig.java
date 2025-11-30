package com.eeit205team5.rentalmanagement.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.eeit205team5.rentalmanagement.security.service.VerificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 之後改成Redis TTL的作法
@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ScheduleConfig {
    private final VerificationService verificationService;

    // 每天凌晨2點清理過期驗證碼
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupExpiredCodes() {
        log.info("開始清理過期驗證碼...");

        verificationService.cleanupExpiredCodes();
    }
}