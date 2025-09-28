package org.example.controllers;

import org.example.enums.AccountType;
import org.example.enums.CurrencyType;
import org.example.enums.ModeRule;
import org.example.enums.OperationType;
import org.example.models.FeeRule;
import org.example.services.FeeruleService;
import org.example.util.InputValidator;

import java.math.BigDecimal;
import java.util.List;

public class FeeruleController {

    private FeeruleService feeruleService;
    public FeeruleController(FeeruleService feeruleService) {
        this.feeruleService = feeruleService;
    }

    public void addFeeRule() {
        System.out.println("Adding FeeRule");

        OperationType operationType = null;
        while (operationType == null) {
            int choiceOperationType = InputValidator.getInt(
                    "Select operation type: \n" +
                            "1. DEPOSIT\n" +
                            "2. WITHDRAW\n" +
                            "3. TRANSFER_OUT\n" +
                            "4. TRANSFER_IN\n" +
                            "5. CREDIT_DISBURSEMENT"
            );
            switch (choiceOperationType) {
                case 1 -> operationType = OperationType.DEPOSIT;
                case 2 -> operationType = OperationType.WITHDRAW;
                case 3 -> operationType = OperationType.TRANSFER_OUT;
                case 4 -> operationType = OperationType.TRANSFER_IN;
                case 5 -> operationType = OperationType.CREDIT_DISBURSEMENT;
                default -> System.out.println("Invalid choice, try again.");
            }
        }

        ModeRule modeRule = null;
        while (modeRule == null) {
            int choiceMode = InputValidator.getInt("Select Mode rule:\n1. FIX\n2. PERCENT");
            switch (choiceMode) {
                case 1 -> modeRule = ModeRule.FIXED;
                case 2 -> modeRule = ModeRule.PERCENT;
                default -> System.out.println("Invalid choice, try again.");
            }
        }

        CurrencyType currencyType = null;
        while (currencyType == null) {
            int choiceCurrency = InputValidator.getInt("Select currency:\n1. MAD\n2. EUR\n3. USD");
            switch (choiceCurrency) {
                case 1 -> currencyType = CurrencyType.MAD;
                case 2 -> currencyType = CurrencyType.EUR;
                case 3 -> currencyType = CurrencyType.USD;
                default -> System.out.println("Invalid choice, try again.");
            }
        }

        BigDecimal value = InputValidator.getBigDecimal("Enter value that applies for fee rule (FIX / PERCENT): ");

        boolean isAdd =  this.feeruleService.addFeeRuls(operationType,modeRule,currencyType,value);
        if(isAdd){
            System.out.println("Fee rule added");
        }else{
            System.out.println("Fee rule not added");
        }

    }

    public void listFeerules(){
        List<FeeRule>  feeRules = this.feeruleService.getFeerules();
        feeRules.forEach(System.out::println);
    }
}
