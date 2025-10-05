package org.example.services;

import org.example.enums.CreditStatus;
import org.example.enums.CreditType;
import org.example.enums.SourceType;
import org.example.models.Account;
import org.example.models.Credit;
import org.example.models.FeeRuleCredit;
import org.example.repositories.AccountRepository;
import org.example.repositories.CreditRepository;
import org.example.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.enums.CreditType.COMPOSE;
import static org.example.enums.CreditType.SIMPLE;
import static org.example.enums.SourceType.INTERE;

public class CreditService {

    private CreditRepository creditRepository;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public CreditService(CreditRepository creditRepository , AccountRepository accountRepository ,TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.creditRepository = creditRepository;
        this.transactionRepository = transactionRepository;
    }

    public boolean requestCredit(int account_id, CreditType creditType, BigDecimal amount, int durationMonths, String justification, BigDecimal salary) {
        FeeRuleCredit feeRuleCredit = this.creditRepository.interestRate(creditType, durationMonths);
        Map<String,BigDecimal> result = calculateMonthlyPaymentSimple(feeRuleCredit.getInterest_rate(), amount, durationMonths);

        BigDecimal monthlyPayment  = result.get("monthPayment");
        BigDecimal monthlyInterest = result.get("monthInterest");



        BigDecimal fortyPercentSalary = salary.multiply(new BigDecimal("0.40"));
        boolean eligible = monthlyPayment.compareTo(fortyPercentSalary) <= 0;
        if (!eligible) {
            System.out.println("your salary is too low, isn't sufficient ");
            return false;
        }

        boolean isRequest = this.creditRepository.requestCredit(account_id, creditType, amount, durationMonths, justification, feeRuleCredit.getId(), monthlyPayment, fortyPercentSalary,monthlyInterest);
        if (isRequest) {
            return true;
        } else {
            return false;
        }


    }

//    private BigDecimal calculateMonthlyPaymentCopmose(BigDecimal interestRate, BigDecimal amount, int durationMonths) {
//        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);
//        BigDecimal monthlyRate = interestRate.divide(BigDecimal.valueOf(12), mc).divide(BigDecimal.valueOf(100), mc);
//        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyRate, mc);
//        BigDecimal denominator = BigDecimal.ONE.subtract(
//                BigDecimal.ONE.divide(onePlusR.pow(durationMonths, mc), mc), mc
//        );
//        BigDecimal monthPayment = amount.multiply(monthlyRate, mc).divide(denominator, 2, RoundingMode.HALF_UP);
//
//        BigDecimal remaining = amount;
//
//        System.out.println("Mensualité fixe: " + monthPayment);
//        System.out.println("Mois | Intérêt | Principal payé | Solde restant");
//
//        for (int i = 1; i <= durationMonths; i++) {
//            BigDecimal monthInterest = remaining.multiply(monthlyRate, mc).setScale(2, RoundingMode.HALF_UP);
//            BigDecimal principalPaid = monthPayment.subtract(monthInterest).setScale(2, RoundingMode.HALF_UP);
//            remaining = remaining.subtract(principalPaid).setScale(2, RoundingMode.HALF_UP);
//
//            System.out.printf("%3d | %7s | %14s | %13s%n", i, monthInterest, principalPaid, remaining);
//        }
//
//        return monthPayment;
//    }

    private Map<String,BigDecimal> calculateMonthlyPaymentSimple(BigDecimal interestRate, BigDecimal amount, int durationMonths) {
        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);
        BigDecimal monthlyRate = interestRate.divide(BigDecimal.valueOf(12), mc).divide(BigDecimal.valueOf(100), mc);
        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyRate, mc);
        BigDecimal denominator = BigDecimal.ONE.subtract(
                BigDecimal.ONE.divide(onePlusR.pow(durationMonths, mc), mc), mc
        );
        BigDecimal monthPayment = amount.multiply(monthlyRate, mc).divide(denominator, 2, RoundingMode.HALF_UP);
        BigDecimal monthInterest = amount.multiply(monthlyRate, mc);
        Map<String,BigDecimal> map = new HashMap<>();
        map.put("monthPayment",monthPayment);
        map.put("monthInterest", monthInterest);
        return  map;
    }

    public void deductionMonthly() {
        List<Credit> creditsActifs = this.creditRepository.findActiveCredits();
        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);

        for (Credit credit : creditsActifs) {
            BigDecimal monthlyPayment = credit.getMonthlyPayment();
            BigDecimal remaining = credit.getRemainingAmount();
            Account account = this.accountRepository.getById(credit.getAccountId());

            if (account.getBalance().compareTo(monthlyPayment) < 0) {
                System.out.println("⚠ Account " + credit.getAccountId() + " has insufficient balance for credit " + credit.getId());
                creditRepository.updateCreditAfterPayment(
                        credit.getId(),
                        remaining,
                        credit.getRemaining_months(),
                        CreditStatus.LATE
                );
                continue;
            }

            accountRepository.withdraw(monthlyPayment, account);

            BigDecimal newRemaining = remaining.subtract(monthlyPayment, mc).setScale(2, RoundingMode.HALF_UP);
            if (newRemaining.compareTo(BigDecimal.ZERO) < 0) {
                newRemaining = BigDecimal.ZERO;
            }

            int newDuration = credit.getRemaining_months() - 1;

            CreditStatus newStatus = (newDuration <= 0 || newRemaining.compareTo(BigDecimal.ZERO) == 0)
                    ? CreditStatus.CLOSED
                    : CreditStatus.ACTIVE;

            // Mise à jour du crédit
            creditRepository.updateCreditAfterPayment(
                    credit.getId(),
                    newRemaining,
                    newDuration,
                    newStatus
            );

            BigDecimal interestPart = remaining.multiply(credit.getMonthlyInterest(), mc).setScale(2, RoundingMode.HALF_UP);

            creditRepository.AddRevenueCredit(INTERE, monthlyPayment, credit.getId());

            System.out.println("✅ Payment done for credit " + credit.getId() +
                    " | Principal+Interest: " + monthlyPayment +
                    " | Interest: " + interestPart +
                    " | Remaining: " + newRemaining);
        }
    }

    public List<Credit> creditPending() {
        List<Credit> creditPending = this.creditRepository.findPendingCredits();
        return creditPending;
    }

    public boolean accepteCredit(int id){
         return this.creditRepository.ActiveCredit(id);
    }




}
