package com.mlcalculator;

import java.math.BigDecimal;
import java.util.Optional;

class Calculator {
    private BigDecimal entireCostOfCredit;
    private BankAccount bankAccount;
    private InstalmentCalculator instalmentCalculator;

    Calculator(BigDecimal amountOfCredit, int installments, BigDecimal bankMargin){
        this.entireCostOfCredit = BigDecimal.ZERO;
        this.bankAccount = new BankAccount(amountOfCredit, installments);
        this.instalmentCalculator = new InstalmentCalculator(bankAccount, bankMargin);
    }

    void setWIBOR(BigDecimal wibor){
        this.instalmentCalculator.setWIBOR(wibor);
    }

    void payInstalment(){
        InstalmentDetails instalmentDetails = this.instalmentCalculator.calculateInstalment();
        this.bankAccount.payTheInstalment(instalmentDetails.getCapitalPart());
        this.entireCostOfCredit = this.entireCostOfCredit.add(instalmentDetails.getEntireInstalment());
    }

    void makeAnExcessPayment(BigDecimal payment){
        payment = this.bankAccount.makeAnExcessPayment(payment);
        this.entireCostOfCredit = this.entireCostOfCredit.add(payment);
    }

    Optional<BigDecimal> getEntireCostOfCredit(){
        if(this.bankAccount.isLoanPayed()){
            return Optional.of(this.entireCostOfCredit);
        }
        else{
            return Optional.empty();
        }
    }

    boolean isLoanPayed(){
        return this.bankAccount.isLoanPayed();
    }
}
