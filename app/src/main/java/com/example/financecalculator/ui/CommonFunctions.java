package com.example.financecalculator.ui;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonFunctions {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String FormatToLack(Integer number) {
        if (number == 0){
            return "0";
        }
        List<String> outputString = new ArrayList<>();
        int i = 0;
        while (true){
            if (String.valueOf(number).length() <=2 ){
                if (number > 0){
                    outputString.add(String.valueOf(number));
                }
                break;
            }
            if (i == 0){
                int fraction = number%1000;
                String stringToAdd = String.valueOf(fraction);
                int padding = 0;
                if (fraction < 10) {
                    padding = 2;
                } else if (fraction < 100) {
                    padding = 1;
                }
                while(padding > 0) {
                    stringToAdd = "0" + stringToAdd;
                    padding--;
                }
                outputString.add(stringToAdd);
                number = (int)number/1000;
                i++;
            } else {
                int fraction = number%100;
                String stringToAdd = String.valueOf(fraction);
                int padding = 0;
                if (fraction < 10) {
                    padding = 1;
                }
                while(padding > 0) {
                    stringToAdd = "0" + stringToAdd;
                    padding--;
                }
                outputString.add(stringToAdd);
                number = (int)number/100;
            }
        }
        Collections.reverse(outputString);
        return String.join(",", outputString);
    }
}
