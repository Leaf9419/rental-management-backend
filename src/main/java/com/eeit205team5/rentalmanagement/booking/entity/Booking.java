package com.eeit205team5.rentalmanagement.booking.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.eeit205team5.rentalmanagement.booking.constant.BookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "properties_id")
    private Long propertiesId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "booking_start_time", nullable = false)
    private LocalTime bookingStartTime;

    @Column(name = "booking_end_time", nullable = false)
    private LocalTime bookingEndTime;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    private BookingStatus bookingStatus = BookingStatus.PENDING;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "remark")
    private String remark;

    // 驗證機制，確保結束時間不早於開始時間
    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (bookingEndTime.isBefore(bookingStartTime)) {
            throw new IllegalArgumentException("結束時間不能早於開始時間");
        }
    }

}
