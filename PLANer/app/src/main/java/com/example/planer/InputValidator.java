package com.example.planer;

public class InputValidator {

    // no blank title and description
    static boolean validStringInput(String title, String description){
        if(title.equals("") || description.equals("")){
            return false;
        }
        return true;
    }

    // no non-digit input for number input
    static boolean validNumberInput(String number){
        if(number.equals("")){
            return false;
        }
        if(number.matches(".*[^0-9].*")){
            return false;
        }
        if(Integer.parseInt(number) <= 0){
            return false;
        }
        return true;
    }
}
