package com.example.financecalculator.ui.TaxCalculator;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.financecalculator.R;

import java.util.List;

public class TaxFragment extends Fragment {

    private TaxViewModel taxViewModel;
    private Integer salaryInt;
    private Boolean regime;
    private TableLayout salaryDataTable;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_tax, container, false);
        final TextView salaryText = root.findViewById(R.id.textView2);
        final EditText salaryValue = root.findViewById(R.id.salaryDecimal);
        final Button submitButton = root.findViewById(R.id.button);
        final Switch switch2 = root.findViewById(R.id.switch2);
        salaryDataTable = root.findViewById(R.id.tableTaxSlab);

        switch2.setTextOff("Regime 1");
        switch2.setTextOn("Regime 2");

        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch2.isChecked()) {
                    switch2.setText(R.string.tax_regime_2);
                } else {
                    switch2.setText(R.string.tax_regime_1);
                }
            }
        });
        taxViewModel = ViewModelProviders.of(this).get(TaxViewModel.class);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                View hideView = requireActivity().getCurrentFocus();
                if (hideView != null) {
                    InputMethodManager imm = (InputMethodManager)requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                String localSalary = salaryValue.getText().toString();
                if (localSalary.isEmpty()) {
                    salaryInt = 0;
                } else {
                    salaryInt = Integer.valueOf(localSalary);
                }
                regime = switch2.isChecked();
                taxViewModel.CalculateTax(salaryInt, regime);
//                taxViewModel.CalculateTax(877543, regime);
//                taxViewModel.getSalaryValue().observe(getViewLifecycleOwner(), new Observer<String>() {
//                    @Override
//                    public void onChanged(@Nullable String s) {
//                        textView.setText(s);
//                    }
//                });

                loadDataIntoTable(taxViewModel.getSalaryData());
            }
        });

        return root;
    }


    private void loadDataIntoTable(List<String[]> salaryData) {
        salaryDataTable.removeAllViews();
//        Table Header
        final TableRow header = new TableRow(salaryDataTable.getContext());
        header.setPaddingRelative(50,10,50,10);
        TextView header1 = new TextView(header.getContext());
        TextView header2 = new TextView(header.getContext());
        TextView header3 = new TextView(header.getContext());
        header1.setTextColor(Color.WHITE);
        header2.setTextColor(Color.WHITE);
        header3.setTextColor(Color.WHITE);
        header1.setBackgroundColor(Color.parseColor("#0a318c"));// RBG (10,49,140)
        header2.setBackgroundColor(Color.parseColor("#0a318c"));
        header3.setBackgroundColor(Color.parseColor("#0a318c"));
        header1.setText(R.string.tax_slab_from);
        header2.setText(R.string.tax_slab_to);
        header3.setText(R.string.tax_slab_value);
        header1.setPaddingRelative(50,10,0,10);
        header2.setPaddingRelative(50,10,0,10);
        header3.setPaddingRelative(50,10,50,10);
        header.addView(header1,0);
        header.addView(header2,1);
        header.addView(header3,2);
        salaryDataTable.addView(header, 0);
//        Table Data
        int id = 1;
        for (String[] rowData : salaryData) {
            final TableRow tr = new TableRow(salaryDataTable.getContext());
            tr.setPaddingRelative(50,10,50,10);
            TextView col1 = new TextView(tr.getContext());
            TextView col2 = new TextView(tr.getContext());
            TextView col3 = new TextView(tr.getContext());
            col1.setTextColor(Color.BLACK);
            col2.setTextColor(Color.BLACK);
            col3.setTextColor(Color.BLACK);
            if (id % 2 == 1) {
                col1.setBackgroundColor(Color.WHITE);
                col2.setBackgroundColor(Color.WHITE);
                col3.setBackgroundColor(Color.WHITE);
            } else {
                col1.setBackgroundColor(Color.parseColor("#dff7f7")); // RGB (223,247,247)
                col2.setBackgroundColor(Color.parseColor("#dff7f7"));
                col3.setBackgroundColor(Color.parseColor("#dff7f7"));
            }
            col1.setText(rowData[0]);
            col2.setText(rowData[1]);
            col3.setText(rowData[2]);
            col1.setPaddingRelative(50,10,0,10);
            col2.setPaddingRelative(50,10,0,10);
            col3.setPaddingRelative(50,10,50,10);
            tr.addView(col1,0);
            tr.addView(col2,1);
            tr.addView(col3,2);
            salaryDataTable.addView(tr, id++);
        }
//        Table Footer
        final TableRow footer = new TableRow(salaryDataTable.getContext());
        final TextView footer0 = new TextView(footer.getContext());
        final TextView footer1 = new TextView(footer.getContext());
        final TextView footer2 = new TextView(footer.getContext());
        footer1.setTextColor(Color.WHITE);
        footer2.setTextColor(Color.WHITE);
        footer1.setBackgroundColor(Color.parseColor("#0a318c"));
        footer2.setBackgroundColor(Color.parseColor("#0a318c"));
        footer1.setText(R.string.tax_total_table_end);
        taxViewModel.getSalaryValue().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                footer2.setText(s);
            }
        });
        footer.setPaddingRelative(50,10,50,10);
        footer1.setPaddingRelative(50,10,0,10);
        footer2.setPaddingRelative(50,10,50,10);
        footer.addView(footer0,0);
        footer.addView(footer1,1);
        footer.addView(footer2,2);
        salaryDataTable.addView(footer, id);
    }
}