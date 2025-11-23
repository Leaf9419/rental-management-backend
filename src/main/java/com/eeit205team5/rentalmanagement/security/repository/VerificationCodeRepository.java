package com.eeit205team5.rentalmanagement.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeit205team5.rentalmanagement.security.entity.VerificationCode;
import java.util.List;
import java.time.Instant;


@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByCodeAndType(String code, String type);

    Optional<VerificationCode> findByUserIdAndTypeOrderByCreatedAtDesc(Long userId, String type);

    // 查找已過期但未刪除的驗證碼
    List<VerificationCode> findByExpiresAtBeforeAndUsedFalse(Instant now);

    
}