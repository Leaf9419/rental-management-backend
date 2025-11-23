package com.eeit205team5.rentalmanagement.security.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "gender")
    private String gender; // male、female、other

    @Column(name = "birth_date")
    private Instant birthDate;

    @Column(name = "id_number_hash")
    private String idNumberHash;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "bio")
    private String bio; // 簡介

    @Column(name = "preferred_contact_method")
    private String preferredContactMethod; // email、phone
}