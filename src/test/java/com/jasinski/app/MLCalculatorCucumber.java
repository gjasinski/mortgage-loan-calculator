package com.jasinski.app;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class MLCalculatorCucumber {
    private MLCalculator mlCalculator;
    public MLCalculatorCucumber(){
        this.mlCalculator = new MLCalculator();
    }

    public static class Action {
        public String type, date, value;
        public Action(String type, String date, String value) {
            this.type = type;
            this.date = date;
            this.value = value;
        }
    }

    @Given("^(\\S+) - Kredyt hipoteczny w kwocie (\\S+) PLN, (\\d+) ratach, (\\S+)% prowizji i (\\S+)% WIBORu$")
    public void setUpNewCredit(String dateRaw, String amountOfCreditRaw, int installments, String bankMarginRaw, String wiborRaw){
        this.mlCalculator.setUpNewCredit(dateRaw, amountOfCreditRaw, installments, bankMarginRaw, wiborRaw);
    }

    @When("^Oblicz:$")
    public void operations(List<Action> actions){
        operationsTest(actions);
        System.out.println(this.mlCalculator.payRemainingInstalments());
    }

    @When("^Testuj:$")
    public void operationsTest(List<Action> actions){
        actions.forEach(action -> {
            switch (action.type){
                case "Zmiana WIBOR": this.mlCalculator.changeWIBOR(action.date, action.value);
                    break;
                case "Od teraz będę nadpłacać kredyt w kwocie": this.mlCalculator.changeCurrentExcessPayment(action.date, action.value);
                    break;
                default: throw new IllegalArgumentException(action.type);
            }
        });
    }

    @Then("^Całkowity koszt kredytu powinien wynieść (\\S+) PLN")
    public void entireCostOfCreditShouldBe(String entireCostOfCreditRaw){
        BigDecimal entireCostOfCredit = new BigDecimal(entireCostOfCreditRaw);
        assertEquals(entireCostOfCredit, this.mlCalculator.payRemainingInstalments());
    }
}
