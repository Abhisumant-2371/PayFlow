package com.payflow.notification_service.controller;

import com.payflow.notification_service.entity.Notification;
import com.payflow.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public Notification sendNotification(Notification notification) {
        return notificationService.sendNotification(notification);
    }

    @GetMapping("/{userId}")
    public List<Notification> findByUserId(@PathVariable String userId) {
        Long receiverId = Long.parseLong(userId);
        return notificationService.findByUserId(receiverId);
    }

}
