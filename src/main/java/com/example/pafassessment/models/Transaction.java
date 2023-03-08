package com.example.pafassessment.models;

import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    
    private String transactionId;
    
    private Date date;

    @Size(min= 10, max=10, message="Account number not 10 characters")
    private String fromAccount;

    private String fromAccountName;

    @Size(min= 10, max=10, message="Account number not 10 characters")
    private String toAccount;

    private String toAccountName;

    @Min(value = 0,  message = "The amount must be positive")
    @Min(value = 10,  message = "The minimum transfer is $10.")
    private Float amount;

    private String comments;

    public JsonObject toJsonObject() {
        return Json.createObjectBuilder()
        .add("transactionId", this.getTransactionId())
        .add("date", this.getDate().toString())
        .add("from_account", this.getFromAccount())
        .add("to_account", this.getToAccount())
        .add("amount", this.getAmount())
        .build();
    }

}
