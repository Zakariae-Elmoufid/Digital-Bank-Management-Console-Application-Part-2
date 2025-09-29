package org.example.controllers;

import org.example.interfaces.TransactionInterface;
import org.example.models.Account;
import org.example.models.Client;
import org.example.services.AccountService;
import org.example.services.TransactionServices;
import org.example.util.InputValidator;
import org.example.views.MainMenu;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionController {

    private TransactionServices transactionService;
    private AccountService accountService;
    public TransactionController(TransactionServices transactionServices ,  AccountService accountService) {
        this.transactionService = transactionServices;
        this.accountService = accountService;
    }


    public void deposit() {
        List<Account> accounts = this.accountService.listAllAccount();
        Map<Client, List<Account>> groupedByClient = accounts.stream().collect(Collectors.groupingBy(Account::getClient));

        for(Map.Entry<Client, List<Account>> e : groupedByClient.entrySet()){
            Client client = e.getKey();
            System.out.println(client.getFirstName() + " " + client.getLastName() + " (" + client.getCin() + ")");
            for(Account account : e.getValue()){
                System.out.println(account);
            }
        }

        String rib = InputValidator.getString("Enter rib number");
        Account account = accountService.verifyRib(rib);
        BigDecimal amount = InputValidator.getBigDecimal("Enter amount to be deposited");
        if(account != null){
            boolean isdiposit = this.transactionService.deposit(amount,account);
             if(isdiposit){
                 System.out.println("Deposited successfully");
             }else{
                 System.out.println("Deposited failed");
             }
        }else{
            System.out.println("Account not found");
        }
        new MainMenu().menuTeller();
    }

    public void withdraw() {

        List<Account> accounts = this.accountService.listAllAccount();
        Map<Client, List<Account>> groupedByClient = accounts.stream().collect(Collectors.groupingBy(Account::getClient));
        for(Map.Entry<Client, List<Account>> e : groupedByClient.entrySet()){
            Client client = e.getKey();
            System.out.println(client.getFirstName() + " " + client.getLastName() + " (" + client.getCin() + ")");
            for(Account account : e.getValue()){
                System.out.println(account);
            }
        }

        String rib = InputValidator.getString("Enter rib number");
        Account account = accountService.verifyRib(rib);
        BigDecimal amount = InputValidator.getBigDecimal("Enter amount to be withdrawn");
        if(account != null){
            String withdraw = this.transactionService.withdraw(amount,account);
            System.out.println(withdraw);
        }else{
            System.out.println("Account not found");
        }
        new MainMenu().menuTeller();
    }

}
