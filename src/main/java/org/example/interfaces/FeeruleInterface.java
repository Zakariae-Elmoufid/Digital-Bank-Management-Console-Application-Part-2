package org.example.interfaces;

import org.example.enums.CurrencyType;
import org.example.enums.ModeRule;
import org.example.enums.OperationType;
import org.example.models.FeeRule;

import java.math.BigDecimal;
import java.util.List;

public interface FeeruleInterface {

    public int create(OperationType operationType, ModeRule modeRule, CurrencyType currencyType, BigDecimal value);
    public List<FeeRule> getAll();
}
