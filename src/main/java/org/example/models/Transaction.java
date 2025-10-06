package org.example.models;

import org.example.enums.CurrencyType;
import org.example.enums.TransactionStatus;
import org.example.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Date;

public class Transaction {

    private int id;
    private BigDecimal amount;
    private CurrencyType currency;
    private String discription;
    private Account transferOut;
    private Account transferIn;
    private TransactionStatus status;
    private TransactionType type;
    private LocalDateTime created_at;
    private Date settled_at ;


    public Date getSettled_at() {
        return settled_at;
    }

    public void setSettled_at(Date settled_at) {
        this.settled_at = settled_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }



    public String getTransferOut() {
        return transferOut.getRib();
    }

    public void setTransferOut(Account transferOut) {
        this.transferOut = transferOut;
    }

    public String getTransferIn() {
        return transferIn.getRib();
    }

    public void setTransferIn(Account transferIn) {
        this.transferIn = transferIn;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Transaction(TransactionType type, BigDecimal amount, Account transferOut, Account transferIn, CurrencyType currency ,TransactionStatus status, String discription) {
        this.type = type;
        this.amount = amount;
        this.transferOut = transferOut;
        this.transferIn = transferIn;
        this.currency = currency;
        this.status = status;
        this.discription = discription;
    }

    public Transaction(TransactionType type, BigDecimal amount, Account transferOut, Account transferIn, CurrencyType currency ,TransactionStatus status, String discription,LocalDateTime created_at) {
        this.type = type;
        this.amount = amount;
        this.transferOut = transferOut;
        this.transferIn = transferIn;
        this.currency = currency;
        this.status = status;
        this.discription = discription;
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", currency=" + currency +
                ", discription='" + discription + '\'' +
                ", transferOut=" + getTransferOut() +
                ", transferIn=" + getTransferIn() +
                ", status=" + status +
                ", type=" + type +
                ", created_at=" + created_at +
                ", settled_at=" + settled_at +
                '}';
    }
}
