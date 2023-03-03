package com.example.pafassessment.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.pafassessment.models.Transaction;

@Repository
public class FundsTransferRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String updateFromAccountById = "update accounts set balance = balance - ? where account_id = ?";
    private static final String updateToAccountById = "update accounts set balance = balance + ? where account_id = ?";

    public Transaction transferFunds(Transaction transaction) {
        Float amount = transaction.getAmount();
        String fromAccountId = transaction.getFromAccount();
        String toAccountId = transaction.getToAccount();
        jdbcTemplate.update(updateFromAccountById, amount, fromAccountId);
        jdbcTemplate.update(updateToAccountById, amount, toAccountId);

        return transaction;
    }
    

}
