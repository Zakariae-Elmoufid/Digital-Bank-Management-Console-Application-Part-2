package org.example.models;

import org.example.enums.AccountStatus;
import org.example.enums.AccountType;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

public class Account {
    private int id;
    private String rib;
    private BigDecimal balance;
    private BigDecimal overdraftLimit;
    private AccountType accountType;
    private AccountStatus status;
    private String createdAt;
    private String currency;
    private Client client;

    Random random = new Random();

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }
    public Account(int id, String rib, BigDecimal balance, BigDecimal overdraft_limit, AccountType accountType,AccountStatus status,String createdAt) {
        this.id = id;
        this.rib = rib;
        this.balance = balance;
        this.overdraftLimit = overdraft_limit;
        this.accountType = accountType;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountStatus getStatus() {
        return status ;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public BigDecimal getOverdraft_limit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(BigDecimal overdraft_limit) {
        this.overdraftLimit = overdraft_limit;
    }

    public void  setCurrency(String currency) {
        this.currency = currency;
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", rib='" + rib + '\'' +
                ", balance=" + balance +
                ", overdraftLimit=" + overdraftLimit +
                ", accountType=" + accountType +
                ", status=" + status +
                ", createdAt='" + createdAt + '\'' +
                ", currency='" + currency + '\'' +
                ", client=" + client +
                '}';
    }

    public Account(AccountType accountType, BigDecimal balance) {
        this.rib = "BR_"+random.nextInt(10000)+"_"+random.nextInt(1000) ;
        this.balance = balance;
        this.overdraftLimit= new BigDecimal("0.00") ;
        this.accountType = accountType;
    }


    public void setClient(Client client) {
        this.client = client;
    }
    public Client getClient() {
        return client;
    }


}
