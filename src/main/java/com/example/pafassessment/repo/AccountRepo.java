package com.example.pafassessment.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.pafassessment.models.Account;

@Repository
public class AccountRepo {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String getAllAccountsSQL = "select * from accounts";
    public static final String getAccountByAccountIdSQL = "select * from accounts where account_id = ?";

    public List<Account> getAllAccounts() {
        return jdbcTemplate.query(getAllAccountsSQL, BeanPropertyRowMapper.newInstance(Account.class));
    }

    public boolean doesAccountExistByAccountId(String accountId) {
        Account result = jdbcTemplate.queryForObject(getAccountByAccountIdSQL, BeanPropertyRowMapper.newInstance(Account.class), accountId);
        if (result == null){
            System.out.println("No account found");
            return false;
        } else {
            System.out.println(result.getName() + " name found");
            return true;
        }
    }

    public Account getAccountById(String accountId) {
        Account result = jdbcTemplate.queryForObject(getAccountByAccountIdSQL, BeanPropertyRowMapper.newInstance(Account.class), accountId);
        return result;
    }

    public Float getBalanceByAccountId(String fromAccount) {
        Account result = jdbcTemplate.queryForObject(getAccountByAccountIdSQL, BeanPropertyRowMapper.newInstance(Account.class), fromAccount);
        return result.getBalance();
    }
    
}
