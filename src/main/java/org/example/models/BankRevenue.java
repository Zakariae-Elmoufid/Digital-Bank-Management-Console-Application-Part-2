package org.example.models;

import org.example.enums.CurrencyType;
import org.example.enums.SourceType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BankRevenue {
    private int id;
    private SourceType source;
    private BigDecimal amount;
    private CurrencyType currency;
    private LocalDateTime OccurredAt;

    public BankRevenue(int id, SourceType source, BigDecimal amount, CurrencyType currency) {
        this.id = id;
        this.source = source;
        this.amount = amount;
        this.currency = currency;
    }
    public BankRevenue(int id, SourceType source, BigDecimal amount, CurrencyType currency ,  LocalDateTime OccurredAt) {
        this.id = id;
        this.source = source;
        this.amount = amount;
        this.currency = currency;
        this.OccurredAt = OccurredAt;
    }

    public BankRevenue(Integer o, String name, BigDecimal feeAmount, CurrencyType currencyType) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SourceType getSource() {
        return source;
    }

    public void setSource(SourceType source) {
        this.source = source;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getOccurredAt() {
        return OccurredAt;
    }

    public void setOccurredAt(LocalDateTime occurredAt) {
        OccurredAt = occurredAt;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "BankRevenue{" +
                "id=" + id +
                ", source=" + source +
                ", amount=" + amount +
                ", currency=" + currency +
                ", OccurredAt=" + OccurredAt +
                '}';
    }
}
