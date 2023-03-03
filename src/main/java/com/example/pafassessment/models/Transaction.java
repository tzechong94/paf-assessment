package com.example.pafassessment.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    
    private String transactionId;
    private Date date;
    private String fromAccount;
    private String toAccount;
    private Float amount;
    private String comments;

}
