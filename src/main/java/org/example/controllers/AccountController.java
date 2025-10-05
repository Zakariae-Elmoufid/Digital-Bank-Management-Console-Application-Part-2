package org.example.controllers;

import org.example.enums.AccountType;
import org.example.models.Account;
import org.example.models.Client;
import org.example.services.AccountService;
import org.example.services.ClientService;
import org.example.util.InputValidator;
import org.example.util.Session;
import org.example.views.MainMenu;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class AccountController {

    private AccountService accountService;
    private  ClientService clientService;



    private void redirectByRole() {
        Session session = Session.getInstance();
        Integer roleId = session.getSession("role_id", Integer.class);

        if (roleId == null) {
            System.out.println("⚠ No role_id found in session. Redirect to login.");
            return;
        }

        switch (roleId) {
            case 1 -> new MainMenu().menuAdmin();
            case 2 -> new MainMenu().menuTeller();
            case 3 -> System.out.println("AUDITOR");
            case 4 -> new MainMenu().menuManager();
            default -> System.out.println("Unknown role, please login again.");
        }
    }



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
                    redirectByRole();
                }
            }
        }while (canCreate );

        BigDecimal balance = InputValidator.getBigDecimal("Enter account balance");
        Account account = this.accountService.createAccount(id, type, balance);
        if (account != null) {
            System.out.println("Account  created");
            redirectByRole();
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
        redirectByRole();



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
        redirectByRole();
    }

    public void validateCloseAccount(){
       List<Account> accounts =  accountService.getAccountPendingClosure();
       if(accounts.isEmpty()){
           System.out.println("There is no account to close");
           redirectByRole();
       }
       accounts.stream().forEach(System.out::println);
       int id = InputValidator.getInt("Choose account ID that you want to validate  close");
       boolean isClose =   accountService.validatClose(id);
       if(isClose) {
           System.out.println("Account closed");
           redirectByRole();
       }
       else System.out.println("Account not closed");
    }

}
