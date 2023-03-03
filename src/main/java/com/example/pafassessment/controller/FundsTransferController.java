package com.example.pafassessment.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.pafassessment.models.Account;
import com.example.pafassessment.models.Transaction;
import com.example.pafassessment.service.AccountService;

@Controller
public class FundsTransferController {
    
    @Autowired
    private AccountService accountSvc;

    @GetMapping("/")
    public String getForm(Model model) {
        // List<Accounts> listOfAccounts = getaccounts from mysql
        // List<String> nameAndAccountId = map form listOfAccounts
        // model.addAttribute(null, form)

        List<Account> listOfAccounts = accountSvc.getAllAccounts();
        List<String> listOfNameAndAccountId = new ArrayList<String>();

        for (Account x : listOfAccounts){
            listOfNameAndAccountId.add(x.getName() + " (" + x.getAccountId() +")");
        }

        System.out.println(listOfNameAndAccountId);
        System.out.println(listOfAccounts);
        model.addAttribute("listOfAccounts", listOfAccounts);
        model.addAttribute("transaction", new Transaction());
        return "transferform";
    }

    // @PostMapping("/transfer")
    // public String postForm(Model model) {

    // }
}
