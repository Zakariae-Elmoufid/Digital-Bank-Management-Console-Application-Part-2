package org.example.models;

import org.example.enums.CreditType;

import java.math.BigDecimal;

public class FeeRuleCredit {

    private  int id;
    private CreditType creditType;
    private BigDecimal interest_rate;

    public FeeRuleCredit(int id, CreditType creditType, BigDecimal interestRate) {
        this.id = id;
        this.creditType = creditType;
        this.interest_rate = interestRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    public BigDecimal getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(BigDecimal interest_rate) {
        this.interest_rate = interest_rate;
    }
}
