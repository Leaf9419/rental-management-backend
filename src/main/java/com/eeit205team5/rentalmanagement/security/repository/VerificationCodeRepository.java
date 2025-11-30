package com.eeit205team5.rentalmanagement.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeit205team5.rentalmanagement.security.entity.VerificationCode;

import java.time.Instant;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByCodeAndType(String code, String type);

    Optional<VerificationCode> findByUserIdAndTypeOrderByCreatedAtDesc(Long userId, String type);

    void deleteByUserIdAndType(Long userId, String type);

    void deleteByExpiresAtBeforeAndUsedFalse(Instant now);
    // void deleteByExpiresAtBefore(Instant now);
}