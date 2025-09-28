package org.example.services;

import org.example.enums.CurrencyType;
import org.example.enums.ModeRule;
import org.example.enums.OperationType;
import org.example.models.FeeRule;
import org.example.repositories.FeeruleRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class FeeruleService {
    private FeeruleRepository feeruleRepository;
    public FeeruleService(FeeruleRepository feeruleRepository) {
        this.feeruleRepository = feeruleRepository;
    }

   public boolean addFeeRuls(OperationType operationType, ModeRule modeRule, CurrencyType currencyType, BigDecimal value){
        int lastId  =  this.feeruleRepository.create(operationType,modeRule,currencyType,value);
        if(lastId < 0) return false;
        return true;
   }

   public List<FeeRule> getFeerules(){
        return this.feeruleRepository.getAll();
   }

   public boolean updateFeeRule(int id, Map<String, Object>  infoUpdate){
        return this.feeruleRepository.update(id, infoUpdate);
   }
}
