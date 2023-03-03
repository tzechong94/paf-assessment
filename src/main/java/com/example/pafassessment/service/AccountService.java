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

    public boolean doesAccountExistByAccountId(String accountId) {
        return accountRepo.doesAccountExistByAccountId(accountId);
    }

    public Float getBalanceByAccountId(String fromAccount) {
        return accountRepo.getBalanceByAccountId(fromAccount);
    }

    public Account getAccountById(String accountId) {
        return accountRepo.getAccountById(accountId);

    }
}
