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
}
