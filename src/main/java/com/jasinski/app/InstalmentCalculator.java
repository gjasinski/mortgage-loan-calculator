package com.jasinski.app;

import java.math.BigDecimal;
import java.math.RoundingMode;

class InstalmentCalculator {
    private BankAccount remainingCredit;
    private BigDecimal wibor, bankMargin, currentInstalment;
    private boolean wasWiborChanged;
    private final BigDecimal beginingCredit;
    private final int allInstalments;

    InstalmentCalculator(BankAccount bankAccount, BigDecimal bankMargin){
        if(bankMargin.compareTo(BigDecimal.ZERO) < 0 || bankMargin.compareTo(BigDecimal.ONE) > 0){
            throw new IllegalArgumentException("BankMargin is not in [0,1] range");
        }
        this.remainingCredit = bankAccount;
        this.wibor = this.currentInstalment= BigDecimal.ZERO;
        this.bankMargin = bankMargin;
        this.beginingCredit = this.remainingCredit.getRemainingCredit();
        this.allInstalments = this.remainingCredit.getNumberOfRemainingInstallments();
        this.wasWiborChanged = true;
    }

    void setWIBOR(BigDecimal newWIBOR){
        if(newWIBOR.compareTo(BigDecimal.ZERO) < 0 || newWIBOR.compareTo(BigDecimal.ONE) > 1){
            throw new IllegalArgumentException("WIBOR is not in [0,1] range");
        }
        this.wibor = newWIBOR;
        this.wasWiborChanged = true;
    }

    InstalmentDetails calculateInstalment(){

        BigDecimal constantInstalment;
        if(this.wasWiborChanged){
          this.currentInstalment = constantInstalment = recalculateInstalment();
          this.wasWiborChanged = false;
        }else {
            constantInstalment = this.currentInstalment;
        }
        if(remainingCredit.getRemainingCredit().compareTo(constantInstalment) <= 0 ||
                remainingCredit.getNumberOfRemainingInstallments() == 1){
            return calculateLastInstalment();
        }
        else{
            BigDecimal remainingCredit = this.remainingCredit.getRemainingCredit();
            BigDecimal interest = calculateInterest(remainingCredit);
            BigDecimal capital = (constantInstalment.subtract(interest));
            return new InstalmentDetails(capital, interest);
        }
    }

    private BigDecimal recalculateInstalment(){
        BigDecimal marginPlusWibor = this.bankMargin.add(this.wibor);
        if (marginPlusWibor.compareTo(BigDecimal.ZERO) == 0){
            return this.beginingCredit.divide(BigDecimal.valueOf(this.allInstalments), 10, RoundingMode.HALF_UP);
        }
        BigDecimal marginDivMonths = marginPlusWibor.divide(BigDecimal.valueOf(12), 200, BigDecimal.ROUND_HALF_UP);
        BigDecimal marginDivMonthsPlusOneToN = (marginDivMonths.add(BigDecimal.ONE)).pow(this.allInstalments);
        BigDecimal marginDivMonthsPlusOneToNMinusOne = marginDivMonthsPlusOneToN.subtract(BigDecimal.ONE);
        return this.beginingCredit.multiply(marginDivMonthsPlusOneToN).multiply(marginDivMonths).divide(
                marginDivMonthsPlusOneToNMinusOne, 2, BigDecimal.ROUND_HALF_UP);
    }

    private InstalmentDetails calculateLastInstalment(){
        BigDecimal capital = this.remainingCredit.getRemainingCredit();
        BigDecimal interest = calculateInterest(capital);
        return new InstalmentDetails(capital, interest);
    }

    private BigDecimal calculateInterest(BigDecimal capital) {
        return capital.multiply(this.wibor.add(this.bankMargin)).divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP);
    }

}
