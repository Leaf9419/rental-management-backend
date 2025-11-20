package com.eeit205team5.rentalmanagement.booking.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookable_periods")
public class BookablePeriod {

    @Id
    @Column(name = "bookable_periods_id")
    private Long bookablePeriodsId;

    @Column(name = "landlord_id")
    private Long landlordId;

    @Column(name = "bookable_date")
    private LocalDate bookableDate;

    @Column(name = "bookable_start_time")
    private LocalDateTime bookableStartTime;

    @Column(name = "bookable_end_time")
    private LocalDateTime bookableEndTime;

    public Long getBookablePeriodsId() {
        return bookablePeriodsId;
    }

    public void setBookablePeriodsId(Long bookablePeriodsId) {
        this.bookablePeriodsId = bookablePeriodsId;
    }

    public Long getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(Long landlordId) {
        this.landlordId = landlordId;
    }

    public LocalDate getBookableDate() {
        return bookableDate;
    }

    public void setBookableDate(LocalDate bookableDate) {
        this.bookableDate = bookableDate;
    }

    public LocalDateTime getBookableStartTime() {
        return bookableStartTime;
    }

    public void setBookableStartTime(LocalDateTime bookableStartTime) {
        this.bookableStartTime = bookableStartTime;
    }

    public LocalDateTime getBookableEndTime() {
        return bookableEndTime;
    }

    public void setBookableEndTime(LocalDateTime bookableEndTime) {
        this.bookableEndTime = bookableEndTime;
    }
}
