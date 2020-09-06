package com.example.financecalculator.ui.TaxCalculator;



import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TaxViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> tax;
    private List<String[]> tableData;

    public TaxViewModel() {
        mText = new MutableLiveData<>();
        tax = new MutableLiveData<>();
        mText.setValue("This is Tax Calculator fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getSalaryValue() {
        return tax;
    }

    public List<String[]> getSalaryData() {
        return tableData;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CalculateTax(Integer salary, Boolean regime) {
        int slab = 0;
        int[] rate = new int[0];
        int[] slabs = new int[0];
        if (!regime) {
            rate = new int[]{0, 5, 20, 30};
            slabs = new int[]{0, 250000, 500000, 1000000, 1000000000};
        } else {
            rate = new int[]{0, 5, 10, 15, 20, 25, 30};
            slabs = new int[]{0, 250000, 500000, 750000, 1000000, 1250000, 1500000, 1000000000};
        }
        int tax_inc = 0;
        int tax_ = 0;
        tableData = new ArrayList<>();
        while (salary > tax_inc) {
            int this_slab = slabs[slab + 1] - slabs[slab];
            if (salary < slabs[slab + 1]) {
                this_slab = salary - slabs[slab];
            }
            tax_inc += this_slab;
            tax_ = tax_ + (this_slab * rate[slab] / 100);
            tableData.add(new String[]{FormatToLack(slabs[slab]), FormatToLack(slabs[slab] + this_slab), FormatToLack(this_slab * rate[slab] / 100)});
            slab += 1;
        }
        tax.setValue(FormatToLack(tax_));
    }

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
                Integer fraction = number%1000;
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
            } else if(i==1){
                Integer fraction = number%100;
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
            } else if(i==2){
                Integer fraction = number%100;
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
                i=-1;
            }
            i++;

        }
        Collections.reverse(outputString);
        return String.join(",", outputString);
    }
}