package com.example.pafassessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.pafassessment.models.Account;
import com.example.pafassessment.models.Transaction;
import com.example.pafassessment.service.AccountService;
import com.example.pafassessment.service.FundsTransferService;
import com.example.pafassessment.service.LogAuditService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class FundsTransferController {
    
    @Autowired
    private AccountService accountSvc;

    @Autowired
    private FundsTransferService fundsTransferSvc;

    @Autowired
    private LogAuditService logAuditSvc;

    @ModelAttribute("listOfAccounts")
    public List<Account> getAccountList(){
        return accountSvc.getAllAccounts();
    }

    @GetMapping("/")
    public String getForm(Model model, HttpSession sess, 
    @ModelAttribute("listOfAccounts") List<Account> listOfAccounts) {
        // List<Account> listOfAccounts = accountSvc.getAllAccounts();
        String error = "";
        
        model.addAttribute("transaction", new Transaction());

        // model.addAttribute("listOfAccounts", listOfAccounts);
        model.addAttribute("error", error);
        return "transferform";
    }

    @PostMapping(path = "/transfer", consumes = "application/x-www-form-urlencoded")
    public String postForm(@Valid Transaction transaction, BindingResult bindingResult,
    Model model, @ModelAttribute("listOfAccounts") List<Account> listOfAccounts, HttpSession sess) {

        
        String fromAccount = transaction.getFromAccount();
        String toAccount = transaction.getToAccount();
        Float transferAmount = transaction.getAmount();
        
        // C
        if (bindingResult.hasErrors()){
            // List<Account> listOfAccounts = accountSvc.getAllAccounts();
            
            // model.addAttribute("listOfAccounts", listOfAccounts);
            // System.out.println("both accounts not the same, but binding errors");
            return "transferform"; 
        }

        //C2
        if (toAccount.equals(fromAccount)) {
            // List<Account> listOfAccounts = accountSvc.getAllAccounts();
            // model.addAttribute("listOfAccounts", listOfAccounts);
            // System.out.println("accounts same error");

            String error = "Both accounts are the same. Transaction failed.";
            model.addAttribute("error", error);
            return "transferform";
        } 

        //C0

        if (!accountSvc.doesAccountExistByAccountId(fromAccount) || !accountSvc.doesAccountExistByAccountId(toAccount)){
            // List<Account> listOfAccounts = accountSvc.getAllAccounts();
            // model.addAttribute("listOfAccounts", listOfAccounts);
            // System.out.println("accounts dont exist");

            String error = "Account(s) dont exist";
            model.addAttribute("error", error);
            return "transferform";

        }

        // C5
        Float senderBalance = accountSvc.getBalanceByAccountId(fromAccount);

        if (transferAmount > senderBalance) {
            // List<Account> listOfAccounts = accountSvc.getAllAccounts();
            // model.addAttribute("listOfAccounts", listOfAccounts);
            // System.out.println("Not enough balance!");

            String error = "Not enough funds for the transaction.";
            model.addAttribute("error", error);
            return "transferform";
        }

        Transaction validTransaction = fundsTransferSvc.transferFunds(transaction);
    
        // System.out.println(validTransaction + "result transaction");
        model.addAttribute("validTransaction", validTransaction);
        sess.setAttribute("validTransaction", validTransaction);
        // log to redis here
        logAuditSvc.logTransaction(validTransaction);
        return "redirect:/transfer";

    }

    @GetMapping("/transfer")
    public String formSubmitted(Model model, HttpSession sess) {
        Transaction transaction = (Transaction) sess.getAttribute("validTransaction");
        model.addAttribute("validTransaction", transaction);
        return "summary";
    }

}
