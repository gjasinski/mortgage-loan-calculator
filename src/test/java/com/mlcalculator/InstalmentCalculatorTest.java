package com.mlcalculator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InstalmentCalculatorTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void setUpInvalidBankMarginTest() throws Exception {
        //given
        thrown.expect(IllegalArgumentException.class);
        BankAccount mockedBankAccount = mock(BankAccount.class);
        BigDecimal bankMargin = BigDecimal.valueOf(-0.01);

        //when
        InstalmentCalculator instalmentCalculator = new InstalmentCalculator(mockedBankAccount, bankMargin);
    }

    @Test
    public void setUpInvalidBankMarginTooBigMarginTest() throws Exception {
        //given
        thrown.expect(IllegalArgumentException.class);
        BankAccount mockedBankAccount = mock(BankAccount.class);
        BigDecimal bankBargin = BigDecimal.valueOf(1.5);

        //when
        InstalmentCalculator instalmentCalculator = new InstalmentCalculator(mockedBankAccount, bankBargin);
    }

    @Test
    public void calculateInstallmentNoMarginNoWIBOR() throws Exception{
        //given
        BankAccount mockedBankAccount = mock(BankAccount.class);
        when(mockedBankAccount.getRemainingCredit()).thenReturn(BigDecimal.valueOf(100000.0));
        when(mockedBankAccount.getNumberOfRemainingInstallments()).thenReturn(10);
        InstalmentDetails instalmentDetails = new InstalmentDetails(BigDecimal.valueOf(10000.0), BigDecimal.ZERO);

        //when
        InstalmentCalculator instalmentCalculator = new InstalmentCalculator(mockedBankAccount, BigDecimal.ZERO);

        //then
        assertEquals("Capital part should be 1000000 with no margin and WIBOR", instalmentDetails, instalmentCalculator.calculateInstalment());
    }

    @Test
    public void calculateInstallmentWithMarginNoWIBOR() throws Exception{
        //given
        BankAccount mockedBankAccount = mock(BankAccount.class);
        when(mockedBankAccount.getRemainingCredit()).thenReturn(BigDecimal.valueOf(100000.0));
        when(mockedBankAccount.getNumberOfRemainingInstallments()).thenReturn(10);
        InstalmentDetails instalmentDetails = new InstalmentDetails(BigDecimal.valueOf(9813.93), BigDecimal.valueOf(416.67));

        //when
        InstalmentCalculator instalmentCalculator = new InstalmentCalculator(mockedBankAccount, BigDecimal.valueOf(0.05));

        //then
        assertEquals("Capital part should be 1000000 with margin 500 and no WIBOR", instalmentDetails, instalmentCalculator.calculateInstalment());
    }

    @Test
    public void calculateInstallmentNoMarginWithWIBOR() throws Exception{
        //given
        BankAccount mockedBankAccount = mock(BankAccount.class);
        when(mockedBankAccount.getRemainingCredit()).thenReturn(BigDecimal.valueOf(100000.0));
        when(mockedBankAccount.getNumberOfRemainingInstallments()).thenReturn(10);
        InstalmentDetails instalmentDetails = new InstalmentDetails(BigDecimal.valueOf(9813.93), BigDecimal.valueOf(416.67));
        InstalmentCalculator instalmentCalculator = new InstalmentCalculator(mockedBankAccount, BigDecimal.ZERO);

        //when
        instalmentCalculator.setWIBOR(BigDecimal.valueOf(0.05));

        //then
        assertEquals("Capital part should be 1000000 with no margin and WIBOR 500", instalmentDetails, instalmentCalculator.calculateInstalment());
    }

    @Test
    public void calculateInstallmentWithMarginWithWIBOR() throws Exception{
        //given
        BankAccount mockedBankAccount = mock(BankAccount.class);
        when(mockedBankAccount.getRemainingCredit()).thenReturn(BigDecimal.valueOf(1000000.0));
        when(mockedBankAccount.getNumberOfRemainingInstallments()).thenReturn(20);
        InstalmentDetails instalmentDetails = new InstalmentDetails(BigDecimal.valueOf(46156.59), BigDecimal.valueOf(8333.33));
        InstalmentCalculator instalmentCalculator = new InstalmentCalculator(mockedBankAccount, BigDecimal.valueOf(0.05));

        //when
        instalmentCalculator.setWIBOR(BigDecimal.valueOf(0.05));

        //then
        assertEquals("Capital part should be 1000000 with margin and WIBOR 500", instalmentDetails, instalmentCalculator.calculateInstalment());
    }

    @Test
    public void calculateLastInstalmentRemainingCreditSmallTest() throws Exception{
        //given
        BankAccount mockedBankAccount = mock(BankAccount.class);
        InstalmentDetails instalmentDetails = new InstalmentDetails(BigDecimal.valueOf(49792.53), BigDecimal.valueOf(833.33));
        when(mockedBankAccount.getRemainingCredit()).thenReturn(BigDecimal.valueOf(100000.0));
        when(mockedBankAccount.getNumberOfRemainingInstallments()).thenReturn(2);
        InstalmentCalculator instalmentCalculator = new InstalmentCalculator(mockedBankAccount, BigDecimal.valueOf(0.05));

        //when
        instalmentCalculator.setWIBOR(BigDecimal.valueOf(0.05));
        assertEquals("Instalment should be 55000", instalmentDetails, instalmentCalculator.calculateInstalment());

        BigDecimal capitalPart = BigDecimal.valueOf(34567);
        instalmentDetails = new InstalmentDetails(capitalPart, BigDecimal.valueOf(432.09));
        instalmentCalculator.setWIBOR(BigDecimal.valueOf(0.1));
        when(mockedBankAccount.getRemainingCredit()).thenReturn(capitalPart);
        when(mockedBankAccount.getNumberOfRemainingInstallments()).thenReturn(1);

        //then
        assertEquals("Capital part should be 34567.0 with margin 1728.35 and WIBOR 3456.7", instalmentDetails, instalmentCalculator.calculateInstalment());
    }

    @Test
    public void calculateLastInstalmentWithoutOverpaymentTest() throws Exception{
        //given
        BankAccount mockedBankAccount = mock(BankAccount.class);
        BigDecimal capitalPart = BigDecimal.valueOf(10000.0);
        InstalmentDetails instalmentDetails = new InstalmentDetails(capitalPart, BigDecimal.valueOf(83.33));
        when(mockedBankAccount.getRemainingCredit()).thenReturn(capitalPart);
        when(mockedBankAccount.getNumberOfRemainingInstallments()).thenReturn(1);
        InstalmentCalculator instalmentCalculator = new InstalmentCalculator(mockedBankAccount, BigDecimal.valueOf(0.05));

        //when
        instalmentCalculator.setWIBOR(BigDecimal.valueOf(0.05));

        //then
        assertEquals("Capital part should be 34567.0 with margin 5000.0 and WIBOR 5000.0", instalmentDetails, instalmentCalculator.calculateInstalment());
    }
}