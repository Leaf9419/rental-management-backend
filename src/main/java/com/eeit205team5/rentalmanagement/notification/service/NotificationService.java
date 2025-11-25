package com.eeit205team5.rentalmanagement.notification.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeit205team5.rentalmanagement.notification.entity.Notification;
import com.eeit205team5.rentalmanagement.notification.repository.NotificationRepository;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // 新增 (隨便打打而已，還要改)
    public Notification create(Notification notification) {

        Notification insert = new Notification();
        insert.setUserId(notification.getUserId());
        insert.setNotificationType(notification.getNotificationType());
        insert.setNotificationTitle(notification.getNotificationTitle());
        insert.setNotificationMessage(notification.getNotificationMessage());
        insert.setNotificationStatus("未讀");
        insert.setSentAt(LocalDateTime.now());
        insert.setAccessBy(notification.getAccessBy());

        return notificationRepository.save(notification);
    }
}
