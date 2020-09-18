package com.example.financecalculator.ui.TaxResults;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.financecalculator.ui.CommonFunctions.FormatToLack;

public class PageViewModel extends ViewModel {

    private List<String[]> tableData;
    private MutableLiveData<String> tax = new MutableLiveData<>();;
    private MutableLiveData<Integer> taxableSalary = new MutableLiveData<>();;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    private LiveData<String> mText = Transformations.map(taxableSalary, new Function<Integer, String>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public String apply(Integer input) {
            return "Taxable amount after deductions: " + FormatToLack(input);
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }
    public void setTaxableSalary(Integer salary){
        taxableSalary.setValue(salary);
    }
    public LiveData<Integer> getTaxableSalary() {return taxableSalary;}
    public LiveData<Integer> getIndex() { return mIndex; }
    public LiveData<String> getText() { return mText; }
    public LiveData<String> getSalaryValue() {
        return tax;
    }
    public List<String[]> getSalaryData() {
        return tableData;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CalculateTax(Integer salary, boolean regime, Integer deductions) {
        int[] rate;
        int[] slabs;
        if (regime) {
            rate = new int[]{0, 5, 20, 30};
            slabs = new int[]{0, 250000, 500000, 1000000};
            salary = salary - deductions - 50000;
            if (salary < 0){
                salary = 0;
            }
        } else {
            rate = new int[]{0, 5, 10, 15, 20, 25, 30};
            slabs = new int[]{0, 250000, 500000, 750000, 1000000, 1250000, 1500000};
        }
        setTaxableSalary(salary);
        if(salary <= 500000){
            tax.setValue("0");
            tableData = new ArrayList<>();
            return;
        }
        int tax_inc = 0;
        int tax_ = 0;
        tableData = new ArrayList<>();
        int len = slabs.length;
        int slab = 0;
        while (salary > tax_inc) {
            long this_slab;
            if (slab == len - 1){
                this_slab = salary - slabs[slab];
            } else {
                this_slab = slabs[slab + 1] - slabs[slab];
                if (salary < slabs[slab + 1]) {
                    this_slab = salary - slabs[slab];
                }
            }
            tax_inc += this_slab;
            tax_ = (int) (tax_ + (this_slab * rate[slab] / 100));
            tableData.add(new String[]{FormatToLack(slabs[slab]), FormatToLack((int) (slabs[slab] + this_slab)), FormatToLack((int) (this_slab * rate[slab] / 100))});
            slab += 1;
        }
        tax.setValue(FormatToLack(tax_));
    }
}