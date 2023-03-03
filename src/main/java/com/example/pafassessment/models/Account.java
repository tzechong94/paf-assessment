package com.example.pafassessment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    
    private String accountId;
    private String name;
    private Float balance;

    
}
