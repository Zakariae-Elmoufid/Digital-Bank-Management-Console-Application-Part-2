package org.example.controllers;

import org.example.enums.AccountType;
import org.example.models.Account;
import org.example.models.Client;
import org.example.services.AccountService;
import org.example.services.ClientService;
import org.example.util.InputValidator;

import java.math.BigDecimal;
import java.util.BitSet;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Scanner;

public class AccountController {

    private AccountService accountService;
    private  ClientService clientService;


    public AccountController(AccountService accountService, ClientService clientService) {
        this.accountService = accountService;
        this.clientService = clientService;


    }

    public void createAccount() {
        System.out.println("=== Create Account ===");
        List<Client> clients =  this.clientService.getAllClients();
        clients.stream().forEach(System.out::println);
        int id = InputValidator.getInt("choose client ID that you want to create");

        boolean isValidAccount;
        AccountType type = null;
        boolean canCreate = false;

        do{
            int choice = InputValidator.getInt("Choose account type: 1. SAVINGS  2. CURRENT  3. CREDIT");

            switch (choice) {
                case 1 -> type = AccountType.SAVINGS;
                case 2 -> type = AccountType.CURRENT;
                case 3 -> type = AccountType.CREDIT;
                default -> {
                    System.out.println("Invalid choice");

                }
            }
             canCreate = this.accountService.checkethreeAccountEachUser(id, type);
            if(canCreate){
                System.out.println("Client already has this account type, choose another type");
            }
        }while (canCreate );


        BigDecimal balance = InputValidator.getBigDecimal("Enter account balance");

        Account account = this.accountService.createAccount(id, type, balance);


        if (account != null) {
            System.out.println("Account  created");
        } else {
            System.out.println("Account not created");
        }
    }

}
