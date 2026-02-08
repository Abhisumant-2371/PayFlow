package com.payflow.notification_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id",nullable = false)
    private Long senderId;

    @Column(name = "receiver_id",nullable = false)
    private Long receiverId;

    @Column(nullable = false)
    @Positive(message="Price must be greater than 0")
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column
    private String status;

    @PrePersist
    public void prePersist() {
        if(this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
        if(this.status == null) {
            this.status = TransactionStatus.PENDING.toString();
        }
    }

    @Override
    public String toString() {
        return "Transaction {" +
                "sender:" + this.senderId +
                "receiver:" + this.receiverId +
                "status:" + this.status +
                "amount:" + this.amount;
    }
}
