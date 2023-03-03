package com.example.pafassessment.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pafassessment.models.Account;
import com.example.pafassessment.models.Transaction;
import com.example.pafassessment.repo.FundsTransferRepo;

@Service
public class FundsTransferService {
    
    @Autowired
    private FundsTransferRepo fundsTransferRepo;

    @Autowired
    private AccountService accountSvc;


    public Transaction transferFunds(Transaction transaction) {
        String transactionId= UUID
                        .randomUUID()
                        .toString()
                        .substring(0, 8);
        transaction.setTransactionId(transactionId);

        Date date = new Date();
        transaction.setDate(date);

        Account fromAccount = accountSvc.getAccountById(transaction.getFromAccount());
        Account toAccount = accountSvc.getAccountById(transaction.getToAccount());
        transaction.setFromAccountName(fromAccount.getName());
        transaction.setToAccountName(toAccount.getName());
        return fundsTransferRepo.transferFunds(transaction);
    }

}
