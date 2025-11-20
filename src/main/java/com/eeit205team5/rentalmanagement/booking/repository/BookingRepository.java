package com.eeit205team5.rentalmanagement.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeit205team5.rentalmanagement.booking.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
