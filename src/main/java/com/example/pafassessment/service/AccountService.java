package com.example.pafassessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pafassessment.models.Account;
import com.example.pafassessment.repo.AccountRepo;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    public List<Account> getAllAccounts() {
        return accountRepo.getAllAccounts();
    }
    
}