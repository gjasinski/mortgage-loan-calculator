package com.mlcalculator;

import java.math.BigDecimal;

class InstalmentDetails {
    private final BigDecimal capitalPart, interestPart;

    InstalmentDetails(BigDecimal capitalPart, BigDecimal interestPart){
        if(capitalPart.compareTo(BigDecimal.ZERO) < 0 || interestPart.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Arguments should be greater or equal than 0");
        }
        this.capitalPart = capitalPart;
        this.interestPart = interestPart;
    }

    BigDecimal getCapitalPart(){
        return this.capitalPart;
    }

    BigDecimal getInterestPart(){
        return this.interestPart;
    }

    BigDecimal getEntireInstalment() {
        return this.capitalPart.add(this.interestPart);
    }

    @Override
    public boolean equals(Object other){
        if (this == other){
            return true;
        }
        if (!(other instanceof InstalmentDetails)){
            return false;
        }else{
            InstalmentDetails that = (InstalmentDetails)other;
            return this.capitalPart.compareTo(that.capitalPart) == 0 && this.interestPart.compareTo(that.interestPart) == 0;
        }
    }

    @Override
    public String toString(){
        return "Capital part: " + this.capitalPart.toString() + ", interest part: " + this.interestPart.toString();
    }
}
