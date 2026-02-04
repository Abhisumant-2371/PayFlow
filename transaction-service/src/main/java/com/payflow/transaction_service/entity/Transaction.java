package com.payflow.transaction_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
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
