package org.example.interfaces;

import org.example.enums.CreditType;
import org.example.enums.SourceType;
import org.example.models.Credit;
import org.example.models.FeeRuleCredit;

import java.math.BigDecimal;
import java.util.List;

public interface CreditInterface {
    public boolean requestCredit(int account_id, CreditType creditType, BigDecimal amount, int  durationMonths, String justification,int fee_rule_id,BigDecimal monthlyPayment,BigDecimal fortyPercentSalary,BigDecimal monthlyInterest);
    public FeeRuleCredit interestRate(CreditType creditType, int durationMonths);
    public List<Credit> findActiveCredits();
    public int AddRevenueCredit(SourceType type, BigDecimal monthlyPayment, int  creditId);
    public List<Credit> findPendingCredits();
    public boolean ActiveCredit(int id);


}
