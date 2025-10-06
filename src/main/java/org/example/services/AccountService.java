package org.example.services;

import org.example.enums.AccountType;
import org.example.models.Account;
import org.example.repositories.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {

    private  AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account createAccount(int client_id, AccountType type,BigDecimal balance) {
          Account account = new Account( type,balance);
          int id = accountRepository.create(client_id,account);
          if(id > 0){
              account.setId(id);
              return account;
          }
          return null;
    }

    public boolean checkethreeAccountEachUser(int client_id ,AccountType type) {
        List<Account> accounts = this.accountRepository.getAccountByClientId(client_id);
        boolean exists =  accounts.stream().anyMatch(account -> account.getAccountType().equals(type));
        return exists;
    }

    public List<Account> getAccountByClientId(int client_id){
        return this.accountRepository.getAll();
    }

    public List<Account> listAllAccount() {
        return this.accountRepository.getAll();
    }

    public String closeAccount(String rib){
        List<Account> accounts =  this.accountRepository.getAll();
        Account account = accounts.stream()
                .filter(acc -> acc.getRib().equals(rib))
                .findFirst()
                .orElse(null);
        if(account == null){
            return "Account not found";
        }
        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            return "Balance is more than 0, cannot close account";
        }
        boolean  requestClose  = this.accountRepository.close(rib);
        return "request close has been successfully";

    }

    public Account verifyRib(String rib){
        List<Account> accounts =  this.accountRepository.getAll();
        Account  account = (Account) accounts.stream().filter(acc -> acc.getRib().equals(rib)).findFirst().orElse(null);
        return account;
    }

    public boolean verifyBalance(BigDecimal amount, Account account){
        if(amount.compareTo(account.getBalance()) < 0 ) return true;
        return true;
    }

    public List<Account> ListAccountByTypeCredit(){
        List<Account> accounts =  this.accountRepository.ListAccountByTypeCredit();
        return accounts;
    }

    public List<Account> getAccountPendingClosure(){
        return this.accountRepository.getAccountPendingClosure();
    }
    public boolean validatClose(int id){
       return  this.accountRepository.validateClose(id);
    }
}
