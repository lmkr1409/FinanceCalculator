package com.example.financecalculator.ui.TaxCalculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaxViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> salary;

    public TaxViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Tax Calculator fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Integer> getSalaryValue() {
        salary = new MutableLiveData<>();
        return salary;
    }

    
}