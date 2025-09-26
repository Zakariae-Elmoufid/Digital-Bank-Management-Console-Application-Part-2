package org.example.interfaces;

import org.example.enums.AccountType;
import org.example.models.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountInterface {
    public int create(int client_id, String rib, BigDecimal balance, AccountType type);
    public List<Account> getAccountByClientId(int client_id);


}
