package org.example.interfaces;

import org.example.models.Account;

import java.math.BigDecimal;

public interface TransactionInterface {
    public int deposit(BigDecimal amount, Account account);
}
