package com.eeit205team5.rentalmanagement.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeit205team5.rentalmanagement.booking.entity.BookablePeriod;

@Repository
public interface BookablePeriodRepository extends JpaRepository<BookablePeriod, Long> {

}
