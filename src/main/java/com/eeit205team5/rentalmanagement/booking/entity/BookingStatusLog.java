package com.eeit205team5.rentalmanagement.booking.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking_status_logs")
public class BookingStatusLog {

    @Id
    @Column(name = "booking_status_log_id")
    private Long bookingStatusLogId;

    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "old_status")
    private String oldStatus;

    @Column(name = "now_status")
    private String nowStatus;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "update_by")
    private Long updateBy;

    public Long getBookingStatusLogId() {
        return bookingStatusLogId;
    }

    public void setBookingStatusLogId(Long bookingStatusLogId) {
        this.bookingStatusLogId = bookingStatusLogId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNowStatus() {
        return nowStatus;
    }

    public void setNowStatus(String nowStatus) {
        this.nowStatus = nowStatus;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
}
