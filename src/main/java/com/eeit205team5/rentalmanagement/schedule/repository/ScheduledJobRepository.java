package com.eeit205team5.rentalmanagement.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeit205team5.rentalmanagement.schedule.entity.ScheduledJob;

public interface ScheduledJobRepository extends JpaRepository<ScheduledJob, Long> {

}
