package com.eeit205team5.rentalmanagement.security.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeit205team5.rentalmanagement.common.exception.BusinessException;
import com.eeit205team5.rentalmanagement.email.service.EmailService;
import com.eeit205team5.rentalmanagement.security.entity.VerificationCode;
import com.eeit205team5.rentalmanagement.security.repository.VerificationCodeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationService {
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;

    @Value("${app.verification.code-length}")
    private int codeLength;

    @Value("${app.verification.code-expiration}")
    private long codeExpirationSeconds; // s

    // 安全的亂數產生器
    private static final SecureRandom random = new SecureRandom();

    // 生成隨機驗證碼
    private String generateCode() {
        int bound = (int) Math.pow(10, codeLength); // 1000000
        int code = random.nextInt(bound); // 0~999999

        return String.format("%0" + codeLength + "d", code);
    }

    /**
     * 發送帶有驗證碼的Email
     * 
     * @param email
     * @param userId
     */
    @Transactional
    public void sendEmailVerificationCode(String email, Long userId) {
        // 檢查是否在短時間內重複發送
        if (userId != null) {
            verificationCodeRepository
                    .findByUserIdAndTypeOrderByCreatedAtDesc(userId, "email")
                    .ifPresent(code -> {
                        long diff = Duration.between(code.getCreatedAt(), Instant.now()).getSeconds();

                        if (diff < 60) {
                            throw new BusinessException("驗證碼發送過於頻繁，請稍後再試");
                        }
                    });
        }

        String code = generateCode();
        Instant expiresAt = Instant.now().plus(Duration.ofSeconds(codeExpirationSeconds));

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setUserId(userId);
        verificationCode.setCode(code);
        verificationCode.setType("email");
        verificationCode.setUsed(false);
        verificationCode.setExpiresAt(expiresAt);

        verificationCodeRepository.save(verificationCode);

        // 發送Email
        emailService.sendVerificationCode(email, code);

        log.info("驗證碼已發送: email={}, userId={}", email, userId);
    }

    /**
     * 驗證Email驗證碼
     * 
     * @param email
     * @param code
     * @param userId
     * @return true/false
     */
    @Transactional
    public boolean verifyEmailCode(String email, String code, Long userId) {
        // 查找驗證碼
        // isEmpty()先判斷，再呼叫get()很多餘；如果用ifPresent()又沒有回傳值
        // 這裡改orElseThrow(...)
        VerificationCode verificationCode = verificationCodeRepository
                .findByCodeAndType(code, "email")
                .orElseThrow(() -> new BusinessException("驗證碼錯誤"));

        // 檢查是否屬於該用戶
        if (!userId.equals(verificationCode.getUserId())) {
            throw new BusinessException("驗證碼不匹配");
        }

        // 檢查是否已使用
        if (verificationCode.getUsed()) {
            throw new BusinessException("驗證碼已使用");
        }

        // 檢查是否過期
        if (verificationCode.isExpired()) {
            throw new BusinessException("驗證碼已過期");
        }

        // 標記為已使用
        verificationCode.setUsed(true);
        verificationCode.setUsedAt(Instant.now());
        verificationCodeRepository.save(verificationCode);

        log.info("Email驗證成功: email={}, userId={}", email, userId);

        return true;
    }

    /**
     * 刪除用戶的舊驗證碼(-)
     * 
     * @param userId
     * @param type
     */
    @Transactional
    public void deleteOldCodes(Long userId, String type) {
        verificationCodeRepository.deleteByUserIdAndType(userId, type);

        log.info("刪除舊驗證碼: userId={}, type={}", userId, type);
    }

    /**
     * 清理過期的驗證碼(定期執行)
     */
    @Transactional
    public void cleanupExpiredCodes() {
        verificationCodeRepository.deleteByExpiresAtBeforeAndUsedFalse(Instant.now());
        // verificationCodeRepository.deleteByExpiresAtBefore(Instant.now());

        log.info("已清理過期驗證碼");
    }
}