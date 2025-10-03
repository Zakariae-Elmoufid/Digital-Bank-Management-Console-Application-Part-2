package org.example.models;

import org.example.enums.CreditStatus;
import org.example.enums.CreditType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Credit {
    private int id;
    private BigDecimal   amount;
    private int months;
    private int remaining_months;
    private BigDecimal monthlyPayment;
    private BigDecimal remainingAmount;
    private String justification;
    private CreditType creditType;
    private CreditStatus creditStatus;
    private LocalDate approvatedAt;
    private BigDecimal monthlyInterest;
    private int accountId;

    public Credit(int id, BigDecimal amount, int months, int remaining_months, BigDecimal monthlyPayment, BigDecimal remainingAmount, String justification, CreditType creditType, CreditStatus creditStatus, int accountId, BigDecimal monthInterest) {
        this.id = id;
        this.amount = amount;
        this.months = months;
        this.remaining_months = remaining_months;
        this.monthlyPayment = monthlyPayment;
        this.remainingAmount = remainingAmount;
        this.justification = justification;
        this.creditType = creditType;
        this.creditStatus = creditStatus;
        this.accountId = accountId;
        this.monthlyInterest = monthInterest;

    }

    public BigDecimal getMonthInterest() {
        return monthlyInterest;
    }

    public void setMonthInterest(BigDecimal monthInterest) {
        this.monthlyInterest = monthInterest;
    }

    public BigDecimal getMonthlyInterest() {
        return monthlyInterest;
    }

    public void setMonthlyInterest(BigDecimal monthlyInterest) {
        this.monthlyInterest = monthlyInterest;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

        public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getAccountId(){
        return accountId;
    }
    public void setAccountId(int accountId){
        this.accountId = accountId;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getRemaining_months() {
        return remaining_months;
    }

    public void setRemaining_months(int remaining_months) {
        this.remaining_months = remaining_months;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    public CreditStatus getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(CreditStatus creditStatus) {
        this.creditStatus = creditStatus;
    }




}
