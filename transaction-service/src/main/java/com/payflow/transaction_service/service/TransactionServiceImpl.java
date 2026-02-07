package com.payflow.transaction_service.service;

import com.payflow.transaction_service.entity.Transaction;
import com.payflow.transaction_service.entity.TransactionStatus;
import com.payflow.transaction_service.kafka.KafkaEventProducer;
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

    @Autowired
    private  ObjectMapper objectMapper;

    @Autowired
    private KafkaEventProducer kafkaEventProducer;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.COMPLETED.toString());

        Transaction saved =  transactionRepository.save(transaction);

        try{
            String payload = objectMapper.writeValueAsString(transaction);
            String key = String.valueOf(saved.getId());
            kafkaEventProducer.sendTransactionEvent(key, payload);
        }catch (Exception e){
            e.printStackTrace();
        }

        return saved;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
