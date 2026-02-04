package com.payflow.transaction_service.service;

import com.payflow.transaction_service.entity.Transaction;
import com.payflow.transaction_service.entity.TransactionStatus;
import com.payflow.transaction_service.respository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.COMPLETED.toString());

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
