package com.example.planer;

import static org.junit.Assert.*;

import org.junit.Test;

public class GoalTest {

    // User Story #4, Scenario 2
    @Test
    public void updateGoalsCompletedCounter() {
        Goal goal = new Goal();
        goal.setGoalsCompletedCounter(0);
        String goalsInput = "10";

        if(InputValidator.validNumberInput(goalsInput))
            goal.updateGoalsCompletedCounter(Integer.parseInt(goalsInput));

        assertEquals(10, goal.getGoalsCompletedCounter());
    }

    // User Story #4, Scenario 1
    @Test
    public void noUpdateForGoalsCompletedCounter(){
        Goal goal = new Goal();
        goal.setGoalsCompletedCounter(100);
        String goalsInput = "";

        if(InputValidator.validNumberInput(goalsInput))
            goal.updateGoalsCompletedCounter(Integer.parseInt(goalsInput));

        assertEquals(100, goal.getGoalsCompletedCounter());
    }

}