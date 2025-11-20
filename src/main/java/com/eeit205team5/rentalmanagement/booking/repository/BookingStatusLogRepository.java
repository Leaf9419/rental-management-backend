package com.eeit205team5.rentalmanagement.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeit205team5.rentalmanagement.booking.entity.BookingStatusLog;

public interface BookingStatusLogRepository extends JpaRepository<BookingStatusLog, Long> {

}
