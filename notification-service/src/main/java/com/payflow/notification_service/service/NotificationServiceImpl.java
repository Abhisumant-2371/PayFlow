package com.payflow.notification_service.service;

import com.payflow.notification_service.entity.Notification;
import com.payflow.notification_service.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification sendNotification(Notification notification) {
        notification.setSentAt(LocalDateTime.now());
        log.info("Notification sent at {}", notification.getSentAt());
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findByUserId(Long userid) {
        return notificationRepository.findByUserId(userid);
    }
}
