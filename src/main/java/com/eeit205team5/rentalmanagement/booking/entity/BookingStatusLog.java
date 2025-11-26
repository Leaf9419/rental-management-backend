package com.eeit205team5.rentalmanagement.booking.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.eeit205team5.rentalmanagement.booking.constant.BookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "booking_status_logs")
public class BookingStatusLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_status_log_id")
    private Long bookingStatusLogId;

    @Column(name = "booking_id")
    private Long bookingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", nullable = false)
    private BookingStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "now_status", nullable = false)
    private BookingStatus nowStatus;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "update_by")
    private Long updateBy;

}
