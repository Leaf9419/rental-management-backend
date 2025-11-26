package com.eeit205team5.rentalmanagement.notification.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.eeit205team5.rentalmanagement.booking.constant.NotificationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "notification_title")
    private String notificationTitle;

    @Column(name = "notification_message")
    private String notificationMessage;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status")
    private NotificationStatus notificationStatus = NotificationStatus.QUEUED;

    @CreationTimestamp
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "access_by")
    private String accessBy;

    @Column(name = "payload_json")
    private String payloadJson;

    @Column(name = "response_code")
    private Long responseCode;

}
