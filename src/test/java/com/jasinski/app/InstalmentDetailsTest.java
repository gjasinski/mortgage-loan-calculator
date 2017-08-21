package com.jasinski.app;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class InstalmentDetailsTest {
    @Test
    public void equalTest() throws Exception{
        //given

        //when
        InstalmentDetails instalmentDetails1 = new InstalmentDetails(BigDecimal.valueOf(12345), BigDecimal.valueOf(90));
        InstalmentDetails instalmentDetails2 = new InstalmentDetails(BigDecimal.valueOf(12345), BigDecimal.valueOf(90));

        //then
        assertEquals("Should be equal objects", instalmentDetails1, instalmentDetails2);
    }

    @Test
    public void notEqualTest() throws Exception{
        //given

        //when
        InstalmentDetails instalmentDetails1 = new InstalmentDetails(BigDecimal.valueOf(12345), BigDecimal.valueOf(90.01));
        InstalmentDetails instalmentDetails2 = new InstalmentDetails(BigDecimal.valueOf(12345), BigDecimal.valueOf(90));
        //then
        assertNotEquals("Should be not equal objects", instalmentDetails1, instalmentDetails2);
    }

    @Test
    public void getEntireInterestTest() throws Exception{
        //given

        //when
        InstalmentDetails instalmentDetails = new InstalmentDetails(BigDecimal.valueOf(1000), BigDecimal.valueOf(2000));

        //then
        assertEquals("Entire interest should be 3000", BigDecimal.valueOf(3000), instalmentDetails.getEntireInstalment());
    }
}