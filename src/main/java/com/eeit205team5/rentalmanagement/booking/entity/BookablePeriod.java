package com.eeit205team5.rentalmanagement.booking.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bookable_periods")
public class BookablePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
