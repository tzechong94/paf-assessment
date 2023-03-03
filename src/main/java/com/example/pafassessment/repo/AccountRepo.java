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

    public List<Account> getAllAccounts() {
        return jdbcTemplate.query(getAllAccountsSQL, BeanPropertyRowMapper.newInstance(Account.class));
    }
    
}
