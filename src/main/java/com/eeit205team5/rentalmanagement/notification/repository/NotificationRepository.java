package com.eeit205team5.rentalmanagement.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeit205team5.rentalmanagement.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
