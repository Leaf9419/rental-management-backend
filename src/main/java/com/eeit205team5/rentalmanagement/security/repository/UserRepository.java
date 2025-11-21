package com.eeit205team5.rentalmanagement.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeit205team5.rentalmanagement.security.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    // OAuth2
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}