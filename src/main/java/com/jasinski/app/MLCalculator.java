package com.jasinski.app;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class MLCalculator {
    private Calculator calculator;
    private LocalDate currentDate;
    private BigDecimal currentExcessPayment;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd][d].[MM][M].yyyy");

    public void setUpNewCredit(String dateRaw, String amountOfCreditRaw, int installments, String bankMarginRaw, String wiborRaw){
        BigDecimal amountOfCredit = new BigDecimal(amountOfCreditRaw);
        BigDecimal bankMargin = new BigDecimal(bankMarginRaw).divide(BigDecimal.valueOf(100), 5, BigDecimal.ROUND_HALF_UP);
        BigDecimal wibor = new BigDecimal(wiborRaw).divide(BigDecimal.valueOf(100), 5, BigDecimal.ROUND_HALF_UP);

        this.calculator = new Calculator(amountOfCredit, installments, bankMargin);
        this.currentDate = createDate(dateRaw);
        this.calculator.setWIBOR(wibor);
        this.currentExcessPayment = BigDecimal.ZERO;
    }

    public void changeCurrentExcessPayment(String dateRaw, String newExcessPayment){
        LocalDate date = LocalDate.parse(dateRaw, MLCalculator.formatter);
        payUntilDate(date);
        this.currentExcessPayment = new BigDecimal(newExcessPayment);
    }

    public void changeWIBOR(String dateRaw, String newWiborRaw){
        LocalDate date = LocalDate.parse(dateRaw, MLCalculator.formatter);
        payUntilDate(date);
        BigDecimal newWibor = new BigDecimal(newWiborRaw).divide(BigDecimal.valueOf(100), 5, BigDecimal.ROUND_HALF_UP);
        this.calculator.setWIBOR(newWibor);
    }

    public BigDecimal payRemainingInstalments(){
        while(!this.calculator.isLoanPayed()){

            this.calculator.payInstalment();
            this.calculator.makeAnExcessPayment(this.currentExcessPayment);
        }
        Optional<BigDecimal> entireCostOfCredit = this.calculator.getEntireCostOfCredit();
        if(entireCostOfCredit.isPresent()){
            return entireCostOfCredit.get();
        }
        else{
            throw new IllegalStateException("Error");
        }
    }

     private LocalDate createDate(String rawDate){
        LocalDate date = LocalDate.parse(rawDate, MLCalculator.formatter);
        LocalDate newDate;
        if(date.getDayOfMonth() < 10){
            if(date.getMonthValue() == 1){
                newDate = LocalDate.of(date.getYear() - 1, 12, 10);
            }
            else{
                newDate = LocalDate.of(date.getYear(), date.getMonthValue() - 1, 10);
            }
        }
        else{
            newDate = LocalDate.of(date.getYear(), date.getMonth(), 10);
        }
        return newDate;
    }

    private void payUntilDate(LocalDate newDate){
         this.currentDate = this.currentDate.plusMonths(1);
         while(this.currentDate.compareTo(newDate) < 0 && !this.calculator.isLoanPayed()){
             this.calculator.payInstalment();
             this.calculator.makeAnExcessPayment(this.currentExcessPayment);
             this.currentDate = this.currentDate.plusMonths(1);
        }
        this.currentDate = this.currentDate.minusMonths(1);
    }
}
