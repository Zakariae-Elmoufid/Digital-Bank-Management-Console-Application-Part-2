package org.example.models;

import org.example.enums.AccountType;

import java.math.BigDecimal;
import java.util.Random;

public class Account {
    private int id;
    private String rib;
    private BigDecimal balance;
    private BigDecimal overdraft_limit;
    private AccountType accountType;
    private boolean active;
    private String createdAt;

    Random random = new Random();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }
    public Account(int id, String rib, BigDecimal balance, BigDecimal overdraft_limit, AccountType accountType,boolean active,String createdAt) {
        this.id = id;
        this.rib = rib;
        this.balance = balance;
        this.overdraft_limit = overdraft_limit;
        this.accountType = accountType;
        this.active = active;
        this.createdAt = createdAt;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigDecimal getOverdraft_limit() {
        return overdraft_limit;
    }

    public void setOverdraft_limit(BigDecimal overdraft_limit) {
        this.overdraft_limit = overdraft_limit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public Account(AccountType accountType, BigDecimal balance) {
        this.rib = "BR_"+random.nextInt(10000)+"_"+random.nextInt(1000) ;
        this.balance = balance;
        this.overdraft_limit= new BigDecimal("0.00") ;
        this.accountType = accountType;
    }



}
