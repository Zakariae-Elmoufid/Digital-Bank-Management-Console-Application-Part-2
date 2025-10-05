package org.example.controllers;

import org.example.enums.ClientStatus;
import org.example.interfaces.TransactionInterface;
import org.example.models.Account;
import org.example.models.Client;
import org.example.models.FeeRule;
import org.example.repositories.TransactionRepository;
import org.example.services.AccountService;
import org.example.services.ClientService;
import org.example.services.FeeruleService;
import org.example.services.TransactionServices;
import org.example.util.InputValidator;
import org.example.util.Session;
import org.example.views.MainMenu;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.enums.CurrencyType.MAD;
import static org.example.enums.OperationType.TRANSFER_EXTERNAL;

public class TransactionController {

    private TransactionServices transactionService;
    private AccountService accountService;
    private ClientService clientService;
    private FeeruleService feeruleService;
    public TransactionController(TransactionServices transactionServices ,  AccountService accountService, ClientService clientService , FeeruleService feeruleService) {
        this.transactionService = transactionServices;
        this.accountService = accountService;
        this.clientService = clientService;
        this.feeruleService = feeruleService;
    }


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
                 redirectByRole();
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

        String isWithdraw = "";
        Account account;
        do{
            String rib = InputValidator.getString("Enter rib number");
             account = accountService.verifyRib(rib);
            BigDecimal amount = InputValidator.getBigDecimal("Enter amount to be withdrawn");
            if(account != null){
                isWithdraw = this.transactionService.withdraw(amount,account);
                System.out.println(isWithdraw);
            }
        }while (account == null || isWithdraw == null);
        redirectByRole();
    }

    public void transferIntern(){
        List<Client> clientsActive = this.clientService.getAllClients().stream().filter(c -> c.getStatus().equals(ClientStatus.ACTIVE)).toList();
        System.out.println(clientsActive);

            boolean success = false;

            while (!success) {
                int clientId = InputValidator.getInt("Enter Client ID");
                List<Account> accounts = this.accountService.getAccountByClientId(clientId);
                    if(accounts.size() > 1){
                    System.out.println(accounts);
                    String fromAccountRib = InputValidator.getString("Enter From Account Rib");
                    String toAccountRib = InputValidator.getString("Enter To Account Rib");

                    Account fromAccount = accountService.verifyRib(fromAccountRib);
                    if (fromAccount == null ) System.out.println("From account not found");

                    Account toAccount = accountService.verifyRib(toAccountRib);
                    if (toAccount == null) System.out.println("To account not found");

                    BigDecimal amount = InputValidator.getBigDecimal("Enter Amount ");
                    boolean isEnough = this.accountService.verifyBalance(amount, fromAccount);

                    if (isEnough) {
                        boolean isTransfer = this.transactionService.transferInter(fromAccount, toAccount, amount);
                        if (isTransfer){
                            System.out.println("Transfer successful");
                            success = true;
                        }
                        else System.out.println("Transfer failed");
                    }
                    }
            }
        redirectByRole();


    }

    public void transferExternal()  {
        List<Account> accounts = this.accountService.listAllAccount();
        Map<Client, List<Account>> groupedByClient = accounts.stream().collect(Collectors.groupingBy(Account::getClient));
        for(Map.Entry<Client, List<Account>> e : groupedByClient.entrySet()){
            Client client = e.getKey();
            System.out.println(client.getFirstName() + " " + client.getLastName() + " (" + client.getCin() + ")");
            for(Account account : e.getValue()){
                System.out.println(account);
            }
        }

        boolean success = false;
        while (!success) {
            if(accounts.size() >= 1 ) {
                String fromAccountRib = InputValidator.getString("Enter From Account Rib");
                String toAccountRib = InputValidator.getString("Enter To Account Rib External");

                Account fromAccount = accountService.verifyRib(fromAccountRib);
                if (fromAccount == null ) System.out.println("From account not found");

                Account toAccount = accountService.verifyRib(toAccountRib);
                if (toAccount == null) System.out.println("To account not found");

                BigDecimal amount = InputValidator.getBigDecimal("Enter Amount ");
                String discripption = InputValidator.getString("Enter Discripption");
                FeeRule feeRule = this.feeruleService.calculateFee(TRANSFER_EXTERNAL,MAD,amount);
                BigDecimal feeAmount = feeRule.getValue();
                BigDecimal amountPlusFee =  amount.add(feeAmount);
                boolean isEnough = this.accountService.verifyBalance(amountPlusFee, fromAccount);
                if(isEnough){
                    boolean isTransfer = this.transactionService.transferExternal(fromAccount, toAccount, amount,feeAmount, amountPlusFee,feeRule.getId(),discripption);
                    if(isTransfer){
                        System.out.println("Transfer successful");
                        success = true;
                    }else System.out.println("Transfer failed");
                }
            }else {
                System.out.println("No more accounts found");
            }
        }
        redirectByRole();
    }

}
