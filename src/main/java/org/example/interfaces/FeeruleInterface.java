package org.example.interfaces;

import org.example.enums.CurrencyType;
import org.example.enums.ModeRule;
import org.example.enums.OperationType;
import org.example.models.FeeRule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface FeeruleInterface {

    public int create(OperationType operationType, ModeRule modeRule, CurrencyType currencyType, BigDecimal value);
    public List<FeeRule> getAll();
    public boolean update(int id , Map<String, Object> infoUpdate);
    public boolean activate(int id);
    public boolean deactivate(int id);
    public FeeRule findByOperationTypeAndCurrencyAndIsActive(OperationType operationType,CurrencyType currencyType );
}
