package org.example.interfaces;

import org.example.enums.AccountType;
import org.example.models.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountInterface {
    public int create(int client_id, Account account);
    public List<Account> getAccountByClientId(int client_id);
    public List<Account> getAll();
    public boolean close(String rib);
    public boolean deposit(BigDecimal amount , Account account);
    public boolean withdraw(BigDecimal amount , Account account);
    public List<Account>  ListAccountByTypeCredit();
    public Account getById(int id);


}
