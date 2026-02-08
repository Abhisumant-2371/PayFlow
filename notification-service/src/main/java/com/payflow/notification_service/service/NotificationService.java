package com.payflow.notification_service.service;

import com.payflow.notification_service.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification sendNotification(Notification notification);

    List<Notification> findByUserId(Long senderId);
}
