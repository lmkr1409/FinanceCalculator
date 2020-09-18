package com.example.financecalculator.ui.EMI;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.financecalculator.ui.CommonFunctions.FormatToLack;

public class EMIViewModel extends ViewModel {

    private double emi;
    private List<String[]> tableData;

    public double getEmi() {
        return emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    public List<String[]> getTableData() {
        return tableData;
    }

    public void setTableData(List<String[]> tableData) {
        this.tableData = tableData;
    }

    public float ReadInputStringToFloat(String input) {
        if (input.length() != 0) {
            return Float.parseFloat(input);
        } else {
            return (float) 0.0;
        }
    }

    public int ReadInputStringToInteger(String input) {
        if (input.length() != 0) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException n) {
                return (int) Float.parseFloat(input);
            }
        } else {
            return 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CalculateEMI(double p, float r, int n, String mode) {
        System.out.println(mode);
        r = r / (12 * 100);
        if (mode.equals("Year")) {
            n = n * 12;
        }
        emi = (p * r) / (1 - Math.pow(1 + r, -n));
        setEmi(emi);
        int month = 1;
        tableData = new ArrayList<>();
        double paid_till = 0;
        double total_amount = p;
        while (p > 0){

            long i = Math.round(p * r);
            double principal = emi - i;
            double emi_ = i + principal;
            double closing = p - principal;
            if(closing < 0){
                closing = 0;
            }
            paid_till = paid_till + principal;
            double paid_percent = Math.round(paid_till / total_amount *100 *100)/100.0 ;
            tableData.add(new String[]{
                    FormatToLack(month++),
                    FormatToLack((int) Math.round(p)),
                    FormatToLack((int) Math.round(principal)),
                    FormatToLack((int) Math.round(i)),
                    FormatToLack((int) Math.round(emi_)),
                    FormatToLack((int) Math.round(closing)),
                    FormatToLack((int) Math.round(paid_till)),
                    String.valueOf(paid_percent)
            });
            p =  (p - principal);
        }
        setTableData(tableData);
    }

}