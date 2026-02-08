package com.payflow.notification_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payflow.notification_service.entity.Notification;
import com.payflow.notification_service.entity.Transaction;
import com.payflow.notification_service.repository.NotificationRepository;
import com.payflow.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    public NotificationConsumer(NotificationRepository notificationRepository, ObjectMapper objectMapper) {
        this.notificationRepository = notificationRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "txn-initiated",groupId = "notification-group")
    public void listener(String message) throws JsonProcessingException {
        System.out.println("Received message: " + message);
        Transaction transaction = objectMapper.readValue(message, Transaction.class);
        Notification notification = new Notification();
        String notify = "$" + transaction.getAmount() + " received from " + transaction.getSenderId() + " to " + transaction.getReceiverId();
        notification.setMessage(notify);
        notification.setUserId(transaction.getReceiverId());
        notificationService.sendNotification(notification);
    }
}
