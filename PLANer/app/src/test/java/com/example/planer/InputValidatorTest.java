package com.example.planer;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputValidatorTest {

    @Test
    public void dailyNumberIsZeroIsInvalid(){
        int dailyNumber = 0;
        boolean result = InputValidator.validateDailyNumber(dailyNumber);
        assertEquals(false, result);
    }

    @Test
    public void dailyNumberIsNegativeIsInvalid(){
        int dailyNumber = -1;
        boolean result = InputValidator.validateDailyNumber(dailyNumber);
        assertEquals(false, result);
    }

    @Test
    public void dailyNumberIsPositiveIsValid(){
        int dailyNumber = 1;
        boolean result = InputValidator.validateDailyNumber(dailyNumber);
        assertEquals(true, result);
    }
}