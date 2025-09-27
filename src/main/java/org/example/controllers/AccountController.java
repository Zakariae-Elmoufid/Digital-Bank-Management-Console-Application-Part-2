package org.example.controllers;

import org.example.enums.AccountType;
import org.example.models.Account;
import org.example.models.Client;
import org.example.services.AccountService;
import org.example.services.ClientService;
import org.example.util.InputValidator;
import org.example.views.MainMenu;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

                int retryChoice = InputValidator.getInt(
                        "Would you like to try another type or exit?\n" +
                                "1. Try again\n" +
                                "0. Exit"
                );

                if (retryChoice == 0) {
                    new MainMenu().menuTeller();
                }
            }
        }while (canCreate );

        BigDecimal balance = InputValidator.getBigDecimal("Enter account balance");
        Account account = this.accountService.createAccount(id, type, balance);
        if (account != null) {
            System.out.println("Account  created");
            new MainMenu().menuTeller();
        } else {
            System.out.println("Account not created");
        }
    }

    public void listAllAccount(){
       List<Account> accounts =  this.accountService.listAllAccount();

        Map<Client, List<Account>> groupedByClient = accounts.stream().collect(Collectors.groupingBy(Account::getClient));

        for(Map.Entry<Client, List<Account>> entry : groupedByClient.entrySet()){
            Client client = entry.getKey();
            System.out.println(client.getFirstName() + " " + client.getLastName() + " (" + client.getCin() + ")");
            for (Account account : entry.getValue()) {
                System.out.println("   -> " + account.getRib() + " | Balance: " + account.getBalance()+"  |  Type Account: " + account.getAccountType());
            }
        }
        new MainMenu().menuTeller();

    }

    public void closeAccount(){
        List<Account> accounts =  this.accountService.listAllAccount();

        Map<Client, List<Account>> groupedByClient = accounts.stream().collect(Collectors.groupingBy(Account::getClient));

        for(Map.Entry<Client, List<Account>> entry : groupedByClient.entrySet()){
            Client client = entry.getKey();
            System.out.println(client.getFirstName() + " " + client.getLastName() + " (" + client.getCin() + ")");
            for (Account account : entry.getValue()) {
                System.out.println("   -> " + account.getRib() + " | Balance: " + account.getBalance()+"  |  Type Account: " + account.getAccountType()+"  | Status: "+account.getStatus());
            }
        }
        String rib = InputValidator.getString("Choose Rib Account  that you want to close");
        String resultMessage= this.accountService.closeAccount(rib);
        System.out.println(resultMessage);
        new MainMenu().menuTeller();
    }

}
