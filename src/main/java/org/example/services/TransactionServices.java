package org.example.services;

import org.example.models.Account;
import org.example.models.Transaction;
import org.example.repositories.AccountRepository;
import org.example.repositories.TransactionRepository;

import java.math.BigDecimal;

public class TransactionServices {

    private  TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    public TransactionServices(TransactionRepository transactionRepository , AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = new AccountRepository();
    }


    public boolean deposit(BigDecimal amount, Account account){
        this.accountRepository.deposit(amount, account);
        int  lastId =  transactionRepository.deposit(amount, account);
        if(lastId < 0) return false;

        return true;

    }



}
