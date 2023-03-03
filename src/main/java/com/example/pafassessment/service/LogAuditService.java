package com.example.pafassessment.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.pafassessment.models.Transaction;

@Service
public class LogAuditService {
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    // public void logTransaction(Transaction transaction) {
    //     Map<String, String> request = new HashMap<>();
    //     request.put("transactionId", transaction.getTransactionId());
    //     request.put("date", transaction.getDate().toString());
    //     request.put("from_account", transaction.getFromAccount());
    //     request.put("to_account", transaction.getToAccount());
    //     request.put("amount", transaction.getAmount().toString());
        
    //     redisTemplate.opsForHash().putAll(transaction.getTransactionId(), request);
    //     System.out.println(redisTemplate.opsForHash());
    // }

    public void logTransaction(Transaction transaction) {
        transaction.toJsonObject();
        String json = transaction.toString();
        System.out.println(json + "Json value");
        redisTemplate.opsForValue().set(transaction.getTransactionId(), json);
    }


    

}
