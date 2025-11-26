package com.eeit205team5.rentalmanagement.notification.service;

import org.springframework.stereotype.Service;

import com.eeit205team5.rentalmanagement.notification.entity.Notification;
import com.eeit205team5.rentalmanagement.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    /**
     * 新增通知
     * 
     * @param notification 從 Controller 或 DTO 傳入的 Notification 物件
     * @return 新增後的 Notification
     */
    public Notification create(Notification notification) {
        // 使用 Builder 建立新的 Notification 物件
        Notification insert = Notification.builder()
                .userId(notification.getUserId())
                .notificationType(notification.getNotificationType())
                .notificationTitle(notification.getNotificationTitle())
                .notificationMessage(notification.getNotificationMessage())
                .notificationStatus(notification.getNotificationStatus() != null
                        ? notification.getNotificationStatus()
                        : null) // 如果傳入為 null，Builder.Default 會自動使用 QUEUED
                .accessBy(notification.getAccessBy())
                .payloadJson(notification.getPayloadJson())
                .responseCode(notification.getResponseCode())
                .build();

        // 儲存到資料庫
        return notificationRepository.save(insert);
    }
}
