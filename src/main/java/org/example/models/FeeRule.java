package org.example.models;

import org.example.enums.CurrencyType;
import org.example.enums.ModeRule;
import org.example.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class FeeRule {

    private int id;
    private OperationType operationType;
    private ModeRule mode;
    private BigDecimal value;
    private CurrencyType currency;
    private boolean active;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;



    public FeeRule(int id, OperationType operationType, ModeRule mode, BigDecimal value, CurrencyType currency, boolean active, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.operationType = operationType;
        this.mode = mode;
        this.value = value;
        this.currency = currency;
        this.active = active;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "FeeRule{" +
                "id=" + id +
                ", operationType=" + operationType +
                ", mode=" + mode +
                ", value=" + value +
                ", currency=" + currency +
                ", active=" + active +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }

    public FeeRule(OperationType operationType, ModeRule modeRule, CurrencyType currencyType, BigDecimal value) {
       this.operationType = operationType;
       this.mode = modeRule;
       this.currency = currencyType;
       this.value = value;
    }


    public OperationType getOperationType() {
        return operationType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModeRule getMode() {
        return mode;
    }

    public void setMode(ModeRule mode) {
        this.mode = mode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
