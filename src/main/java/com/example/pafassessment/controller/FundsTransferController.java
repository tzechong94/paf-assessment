package com.example.pafassessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.pafassessment.models.Account;
import com.example.pafassessment.models.Transaction;
import com.example.pafassessment.service.AccountService;
import com.example.pafassessment.service.FundsTransferService;

import jakarta.validation.Valid;

@Controller
public class FundsTransferController {
    
    @Autowired
    private AccountService accountSvc;

    @Autowired
    private FundsTransferService fundsTransferSvc;
    
    @GetMapping("/")
    public String getForm(Model model) {
        // List<Accounts> listOfAccounts = getaccounts from mysql
        // List<String> nameAndAccountId = map form listOfAccounts
        // model.addAttribute(null, form)

        List<Account> listOfAccounts = accountSvc.getAllAccounts();
        // List<String> listOfNameAndAccountId = new ArrayList<String>();

        // for (Account x : listOfAccounts){
        //     listOfNameAndAccountId.add(x.getName() + " (" + x.getAccountId() +")");
        // }
        String error = "";
        // System.out.println(listOfNameAndAccountId);
        // System.out.println(listOfAccounts);
        model.addAttribute("listOfAccounts", listOfAccounts);
        model.addAttribute("error", error);
        model.addAttribute("transaction", new Transaction());
        return "transferform";
    }

    @PostMapping(path = "/transfer", consumes = "application/x-www-form-urlencoded")
    public String postForm(@Valid Transaction transaction, BindingResult bindingResult,
    Model model) {

        String fromAccount = transaction.getFromAccount();
        String toAccount = transaction.getToAccount();
        Float transferAmount = transaction.getAmount();
        
        // String fromAccount = form.getFirst("fromAccount");
        // String toAccount = form.getFirst("toAccount");

        if (bindingResult.hasErrors()){
            List<Account> listOfAccounts = accountSvc.getAllAccounts();
            
            // System.out.println(listOfAccounts);
            model.addAttribute("listOfAccounts", listOfAccounts);
            System.out.println("both accounts not the same, but binding errors");
            return "transferform"; 
        }

        if (toAccount.equals(fromAccount)) {
            List<Account> listOfAccounts = accountSvc.getAllAccounts();
            model.addAttribute("listOfAccounts", listOfAccounts);
            System.out.println("accounts same error");

            String error = "Both accounts are the same. Transaction failed.";
            model.addAttribute("error", error);
            return "transferform";
        } 

        // accountSvc.findAccountByAccountId(toAccount);
        // accountSvc.findAccountByAccountId(fromAccount);

        if (!accountSvc.doesAccountExistByAccountId(fromAccount) || !accountSvc.doesAccountExistByAccountId(toAccount)){
            List<Account> listOfAccounts = accountSvc.getAllAccounts();
            model.addAttribute("listOfAccounts", listOfAccounts);
            System.out.println("accounts dont exist");

            String error = "Account(s) dont exist";
            model.addAttribute("error", error);
            return "transferform";

        }

        Float senderBalance = accountSvc.getBalanceByAccountId(fromAccount);

        if (transferAmount > senderBalance) {
            List<Account> listOfAccounts = accountSvc.getAllAccounts();
            model.addAttribute("listOfAccounts", listOfAccounts);
            System.out.println("Not enough balance!");

            String error = "Not enough funds for the transaction.";
            model.addAttribute("error", error);
            return "transferform";
        }

        // System.out.println(fromAccount + " from account");
        // System.out.println(toAccount + " to account");
        Transaction validTransaction = fundsTransferSvc.transferFunds(transaction);
    
        System.out.println(validTransaction + "result transaction");
        model.addAttribute("validTransaction", validTransaction);
        return "summary";

    }


}
