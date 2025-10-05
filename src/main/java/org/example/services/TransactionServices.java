package org.example.services;

import org.example.enums.SourceType;
import org.example.models.Account;
import org.example.models.Transaction;
import org.example.repositories.AccountRepository;
import org.example.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;




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


    public String withdraw(BigDecimal amount, Account account){
        if(amount.compareTo(account.getBalance()) > 0 )return "Insufficient funds" ;
        boolean isWithdraw =  this.accountRepository.withdraw(amount , account);
        if(isWithdraw){
            int lastId =  transactionRepository.withdraw(amount, account);
            return "Successfully Withdrawn";
        }
        else return "failed to Withdraw";

    }

    public boolean transferInter(Account fromAccount,Account toAccount,BigDecimal amount){
        boolean isWithdrawn = this.accountRepository.withdraw(amount, fromAccount);
        boolean isDeposit = this.accountRepository.deposit(amount, toAccount);
        if(isWithdrawn && isDeposit){
            int lastId =  transactionRepository.transferInter(amount,fromAccount, toAccount);
            return true;
        }else return false;
    }

    public boolean transferExternal(Account fromAccount,Account toAccount,BigDecimal amount ,BigDecimal feeAmount , BigDecimal amountPlusFee,int feeRuleId ,String discripption)  {
        boolean isWithdrawn = this.accountRepository.withdraw(amountPlusFee, fromAccount);
        boolean isDeposit = this.accountRepository.deposit(amount, toAccount);
        if(isWithdrawn && isDeposit){
            int transaction_id =  transactionRepository.transferExternal(fromAccount, toAccount ,amount,feeAmount ,amountPlusFee,discripption ,feeRuleId);
            int bankRevenue = transactionRepository.AddRevenueTransaction(SourceType.EXTERNAL_TRANSFER,feeAmount,transaction_id);
            return true;
        }else return false;
    }

    public List<Transaction> historic(){
        return transactionRepository.getAll();
    }





}
