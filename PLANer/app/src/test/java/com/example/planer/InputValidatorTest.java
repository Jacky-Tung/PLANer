package com.example.planer;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputValidatorTest {

    // User Story #4, Scenario 2
    @Test
    public void validNumberInput() {
        String validInput = "100";
        boolean result = InputValidator.validNumberInput(validInput);
        assertEquals(true, result);
    }

    // User Story #4, Scenario 1
    @Test
    public void invalidNumberInput(){
        String invalidInput = "199aa";
        boolean result = InputValidator.validNumberInput(invalidInput);
        assertEquals(false, result);
    }

    // User Story #4, Scenario 1
    @Test
    public void emptyInput(){
        String empty = "";
        boolean result = InputValidator.validNumberInput(empty);
        assertEquals(false, result);
    }

    // User Story #4, Scenario 1
    @Test
    public void lettersInput(){
        String letters = "abc";
        boolean result = InputValidator.validNumberInput(letters);
        assertEquals(false, result);
    }
}