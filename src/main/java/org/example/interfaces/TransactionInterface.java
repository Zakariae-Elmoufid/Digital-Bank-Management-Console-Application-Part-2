package org.example.interfaces;

import org.example.models.Account;
import org.example.models.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionInterface {
    public int deposit(BigDecimal amount, Account account);
    public int withdraw(BigDecimal amount, Account account);
    public int transferInter(BigDecimal amount, Account fromAccount, Account toAccount);
    public int transferExternal(Account fromAccount, Account toAccount ,BigDecimal amount,BigDecimal feeAmount, BigDecimal amountPlusFee,String description,int feeRuleId);
    public List<Transaction> getAll();
}
