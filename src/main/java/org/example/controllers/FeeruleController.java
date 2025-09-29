package org.example.controllers;

import org.example.enums.AccountType;
import org.example.enums.CurrencyType;
import org.example.enums.ModeRule;
import org.example.enums.OperationType;
import org.example.models.FeeRule;
import org.example.services.FeeruleService;
import org.example.util.InputValidator;
import org.example.views.MainMenu;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                            "3. TRANSFER_INTERNAL\n" +
                            "4. TRANSFER_EXTERNAL\n" +
                            "5. CREDIT_DISBURSEMENT"
            );
            switch (choiceOperationType) {
                case 1 -> operationType = OperationType.DEPOSIT;
                case 2 -> operationType = OperationType.WITHDRAW;
                case 3 -> operationType = OperationType.TRANSFER_INTERNAL;
                case 4 -> operationType = OperationType.TRANSFER_EXTERNAL;
                case 5 -> operationType = OperationType.CREDIT_DISBURSEMENT;
                default -> System.out.println("Invalid choice, try again.");
            }
        }

        ModeRule modeRule = null;
        while (modeRule == null) {
            int choiceMode = InputValidator.getInt("Select Mode rule:\n1. FIX\n2. PERCENT");
            switch (choiceMode) {
                case 1 -> modeRule = ModeRule.FIX;
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
        new MainMenu().menuAdmin();
    }

    public void listFeerules(){
        List<FeeRule>  feeRules = this.feeruleService.getFeerules();
        feeRules.forEach(System.out::println);
    }
    
    public void  updateFeeRule(){
        this.listFeerules();
        int id = InputValidator.getInt("Enter ID to update");

        Map<String,Object> infoUpdate = new HashMap<>();

        boolean continuee = true;
        do {
            System.out.println("1. value");
            System.out.println("2. currency");
            System.out.println("3. type operation");
            System.out.println("4. mode");


            int choice = InputValidator.getInt("Enter choice to update");

            switch (choice) {
                case 1:
                    infoUpdate.put("value",InputValidator.getBigDecimal("Entre new value"));
                    break;
                case 2:
                    infoUpdate.put("currency",InputValidator.getString("Entre new type currency {'MAD', 'EUR', 'USD'}"));
                    break;
                case 3:
                    infoUpdate.put("operation_type",InputValidator.getString("Entre new operation { 'DEPOSIT' 'WITHDRAW','TRANSFER_INTERNAL','TRANSFER_EXTERNAL','CREDIT_DISBURSEMENT'} "));
                    break;
                case 4:
                    infoUpdate.put("mode",InputValidator.getString("Enter new Mode"));
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
            continuee = InputValidator.getBoolean("would you like update author info? \ntrue. yes \nfalse. no") ;
        }while (continuee);

         boolean isUpdate = this.feeruleService.updateFeeRule(id,infoUpdate);
         if(isUpdate){
             System.out.println("Fee rule updated");
         }else{
             System.out.println("Fee rule not updated");
         }
        new MainMenu().menuAdmin();

    }

    public void activeFeeRule(){
        List<FeeRule>  feeRules = this.feeruleService.getFeerules();
        feeRules.stream().filter(f->f.isActive() == false).forEach(System.out::println);

        int id = InputValidator.getInt("Enter ID to active fee rule");

        boolean isActive = this.feeruleService.activeRule(id);
        if(isActive) System.out.println("Fee rule activated");
        else System.out.println("Fee rule not activated");
        new MainMenu().menuAdmin();
    }
    public void deactivateFeeRule(){
        List<FeeRule>  feeRules = this.feeruleService.getFeerules();
        feeRules.stream().filter(f->f.isActive() == true).forEach(System.out::println);

        int id = InputValidator.getInt("Enter ID to deactivate fee rule");
        boolean isDeactive = this.feeruleService.deactivateRule(id);
        if(isDeactive) System.out.println("Fee rule deactivated");
        else System.out.println("Fee rule not deactivated");
        new MainMenu().menuAdmin();

    }
}
