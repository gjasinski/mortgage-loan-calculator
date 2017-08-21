package com.jasinski.app;


import java.math.BigDecimal;

class BankAccount {
    private BigDecimal remainingCredit;
    private int numberOfRemainingInstallments;

    BankAccount(BigDecimal amountOfCredit, int numberOfInstalments){
        if(amountOfCredit.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount of credit should be greater than 0");
        }
        if(numberOfInstalments <= 0){
            throw new IllegalArgumentException("Number of instalments should be greater than 0");
        }
        this.remainingCredit = amountOfCredit;
        this.numberOfRemainingInstallments = numberOfInstalments;
    }

    void payTheInstalment(BigDecimal instalment){
        if(this.numberOfRemainingInstallments == 0){
            throw new IllegalArgumentException("All instalments had been paid, remaining credit " + remainingCredit.doubleValue());
        }
        if(instalment.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Instalment should be greater than 0");
        }
        BigDecimal newRemainingCredit =  remainingCredit.subtract(instalment);
        if(newRemainingCredit.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Remaining credit - instalment should be greater or equal 0");
        }
        this.remainingCredit = newRemainingCredit;
        this.numberOfRemainingInstallments--;
    }

    BigDecimal makeAnExcessPayment(BigDecimal payment){
        if(payment.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Payment should be greater or equal 0");
        }
        BigDecimal newRemainingCredit =  remainingCredit.subtract(payment);
        if(newRemainingCredit.compareTo(BigDecimal.ZERO) < 0){
            payment = this.remainingCredit;
            this.remainingCredit = BigDecimal.ZERO;
        }
        else{
            this.remainingCredit = newRemainingCredit;
        }
        return payment;
    }

    BigDecimal getRemainingCredit(){
        return this.remainingCredit;
    }

    int getNumberOfRemainingInstallments(){
        return this.numberOfRemainingInstallments;
    }

    boolean isLoanPayed(){
        return this.remainingCredit.compareTo(BigDecimal.ZERO) == 0;
    }
}
