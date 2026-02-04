package com.payflow.transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransferRequest {
    private String senderName;
    private String receiverName;
    private Double amount;

}
