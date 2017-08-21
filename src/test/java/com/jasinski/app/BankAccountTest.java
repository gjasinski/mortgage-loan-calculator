package com.jasinski.app;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BankAccountTest {
    @Test
    public void setUpAmountOfCreditAndThenAccessItTest() throws Exception{
        //given
        BigDecimal remainingCredit = BigDecimal.valueOf(100000.0);

        //when
        BankAccount bankAccount = new BankAccount(remainingCredit, 2);

        //then
        assertEquals("Remaining credit should be 100000.00", remainingCredit, bankAccount.getRemainingCredit());
        assertEquals("Remaining number of instalments should be 2", 2,
                bankAccount.getNumberOfRemainingInstallments());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void setUpInvalidAmountOfCreditShouldThrowIllegalArgumentExceptionWithNegativeArgumentTest() throws
            Exception{
        //given
        thrown.expect(IllegalArgumentException.class);
        BigDecimal remainingCredit = BigDecimal.valueOf(-100000.0);

        //when
        BankAccount bankAccount = new BankAccount(remainingCredit, 1);
    }

    @Test
    public void setUpInvalidAmountOfCreditShouldThrowIllegalArgumentExceptionWithZeroArgumentTest() throws Exception{
        //given
        thrown.expect(IllegalArgumentException.class);
        BigDecimal remainingCredit = BigDecimal.valueOf(100000.0);

        //when
        BankAccount bankAccount = new BankAccount(remainingCredit, 0);
    }

    @Test
    public void payThreeInstalmentsAndGetSmallerRemainingCreditTest() throws Exception{
        //given
        BigDecimal credit = BigDecimal.valueOf(100000.0);
        BigDecimal remainingCredit = BigDecimal.valueOf(99970.0);
        BankAccount bankAccount = new BankAccount(credit, 10);

        //when
        bankAccount.payTheInstalment(BigDecimal.TEN);
        bankAccount.payTheInstalment(BigDecimal.TEN);
        bankAccount.payTheInstalment(BigDecimal.TEN);

        //then
        assertEquals("Remaining credit should 99970.00", remainingCredit, bankAccount.getRemainingCredit());
        assertEquals("Remaining instalments should be 7", 7, bankAccount.getNumberOfRemainingInstallments());
    }

    @Test
    public void payOffCreditTest() throws Exception{
        //given
        BigDecimal remainingCredit = BigDecimal.valueOf(100000.0);
        BigDecimal instalment = BigDecimal.valueOf(100);
        BankAccount bankAccount = new BankAccount(remainingCredit, 1000);

        //when
        for(int i = 0; i < 1000; i++){
            bankAccount.payTheInstalment(instalment);
        }

        //then
        assertEquals("Remaining credit should be 0.0", BigDecimal.valueOf(0.0), bankAccount.getRemainingCredit());
        assertEquals("Remaining number of installments should be 0", 0, bankAccount.getNumberOfRemainingInstallments());
    }

    @Test
    public void payTheInstalmentShouldThrowIllegalArgumentExceptionPayToMuchMoneyTest() throws Exception{
        //given
        thrown.expect(IllegalArgumentException.class);
        BigDecimal remainingCredit = BigDecimal.valueOf(100000.0);
        BigDecimal instalment = BigDecimal.valueOf(1000);
        BankAccount bankAccount = new BankAccount(remainingCredit, 1000);

        //when
        for(int i = 0; i < 1000; i++){
            bankAccount.payTheInstalment(instalment);
        }
    }

    @Test
    public void payTheInstalmentShouldThrowIllegalArgumentExceptionPayToManyInstalmentsTest() throws Exception{
        //given
        thrown.expect(IllegalArgumentException.class);
        BigDecimal remainingCredit = BigDecimal.valueOf(100000.0);
        BigDecimal instalment = BigDecimal.valueOf(10);
        BankAccount bankAccount = new BankAccount(remainingCredit, 1000);

        //when
        for(int i = 0; i <= 1000; i++){
            bankAccount.payTheInstalment(instalment);
        }
    }

    @Test
    public void payTheInstalmentShouldThrowIllegalArgumentExceptionPayNegativeArgumentTest() throws Exception{
        //given
        thrown.expect(IllegalArgumentException.class);
        BigDecimal remainingCredit = BigDecimal.valueOf(100000.0);
        BigDecimal instalment = BigDecimal.valueOf(-100);
        BankAccount bankAccount = new BankAccount(remainingCredit, 10);

        //when
        bankAccount.payTheInstalment(instalment);
    }

    @Test
    public void makeAnExcessPaymentWithNoCreditThrowIllegalArgumentExceptionTest() throws Exception{
        //given
        thrown.expect(IllegalArgumentException.class);

        //when
        BankAccount bankAccount = new BankAccount(BigDecimal.ZERO, 0);
        //then
        assertEquals(BigDecimal.ZERO, bankAccount.makeAnExcessPayment(BigDecimal.valueOf(10000.0)));
    }

    @Test
    public void makeAnExcessPaymentTest() throws Exception{
        //given
        BankAccount bankAccount = new BankAccount(BigDecimal.valueOf(10000), 2);

        //when
        bankAccount.makeAnExcessPayment(BigDecimal.valueOf(1000.0));

        //then
        assertEquals("Remaining credit should be 9000", BigDecimal.valueOf(9000.0), bankAccount.getRemainingCredit());
    }
}