package org.example.controllers;

import org.example.enums.CreditType;
import org.example.models.Account;
import org.example.models.Client;
import org.example.services.AccountService;
import org.example.services.ClientService;
import org.example.services.CreditService;
import org.example.services.FeeruleService;
import org.example.util.InputValidator;
import org.example.views.MainMenu;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreditController {

    private CreditService creditService;
    private AccountService accountService;
    private ClientService clientService;
    private FeeruleService feeruleService;
    public CreditController(CreditService creditService , AccountService accountService, ClientService clientService) {
        this.creditService = creditService;
        this.accountService = accountService;
        this.clientService = clientService;
    }


    public void  requestCredit(){
       List<Account> accounts =    this.accountService.ListAccountByTypeCredit();
       if(accounts.isEmpty()){
           System.out.println("No credit accounts found , you must to create a credit account");
           new MainMenu().menuTeller();
       }
       for(Account account:accounts){
           System.out.println("---> client :"+account.getClient());
           System.out.println("  --"+account);
       }
        int account_id = InputValidator.getInt("Enter your account id: ");
        Account account =  accounts.stream().filter(acc -> acc.getId() == account_id).findFirst().orElse(null);;
        int choseType = InputValidator.getInt("Enter Type Credit \n1. SIMPLE.\n2. COMPOSE: ");
        CreditType creditType ;
        switch (choseType){
            case 1 -> creditType = CreditType.SIMPLE;
            case 2 -> creditType = CreditType.COMPOSE;
            default -> creditType = CreditType.SIMPLE;
        }

        BigDecimal amount = InputValidator.getBigDecimal("Enter amount to request credit");
        int durationMonths = InputValidator.getInt("Enter duration months");
        String justification = InputValidator.getString("Enter justification");

        boolean isRequest =  this.creditService.requestCredit(account_id,creditType, amount,durationMonths,justification,account.getClient().getSalary());
        if(isRequest){
            System.out.println("request credit successfully");
        }else {
            System.out.println("request credit failed");
        }
        new MainMenu().menuTeller();

    }


}
