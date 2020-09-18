package com.example.financecalculator.ui.TaxResults;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.financecalculator.R;

import java.util.List;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private TableLayout salaryDataTable;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tax_results, container, false);
        final TextView textView = root.findViewById(R.id.tab_view_text_box1);
        salaryDataTable = root.findViewById(R.id.tableTaxSlab);
        pageViewModel.getText().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               textView.setText(s);

            }
        });

        final boolean[] regime = {true};
        pageViewModel.getIndex().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                regime[0] = integer == 1;
            }
        });
        int salaryInt = 0;
        int deductions = 0;
        Bundle bundle = requireActivity().getIntent().getExtras();
        if (bundle != null) {
            if (bundle.get("Salary") != null) {
                salaryInt = (int) bundle.get("Salary");
                deductions = deductions + (int) bundle.get("value_80C");
                deductions = deductions + (int) bundle.get("value_80CC");
                deductions = deductions + (int) bundle.get("value_hra");
                deductions = deductions + (int) bundle.get("value_home_loan");
                deductions = deductions + (int) bundle.get("value_edu_loan");
                deductions = deductions + (int) bundle.get("value_VIA");
            }
        }
        pageViewModel.CalculateTax(salaryInt, regime[0], deductions);
        loadDataIntoTable(pageViewModel.getSalaryData());
        return root;
    }

    private void loadDataIntoTable(List<String[]> salaryData) {
        salaryDataTable.removeAllViews();
//        Table Header
        final TableRow header = new TableRow(salaryDataTable.getContext());
        header.setPaddingRelative(50, 10, 50, 10);
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
        header1.setPaddingRelative(50, 10, 0, 10);
        header2.setPaddingRelative(50, 10, 0, 10);
        header3.setPaddingRelative(50, 10, 50, 10);
        header.addView(header1, 0);
        header.addView(header2, 1);
        header.addView(header3, 2);
        salaryDataTable.addView(header, 0);
//        Table Data
        int id = 1;
        for (String[] rowData : salaryData) {
            final TableRow tr = new TableRow(salaryDataTable.getContext());
            tr.setPaddingRelative(50, 10, 50, 10);
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
            col1.setPaddingRelative(50, 10, 0, 10);
            col2.setPaddingRelative(50, 10, 0, 10);
            col3.setPaddingRelative(50, 10, 50, 10);
            tr.addView(col1, 0);
            tr.addView(col2, 1);
            tr.addView(col3, 2);
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
        pageViewModel.getSalaryValue().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                footer2.setText(s);
            }
        });
        footer.setPaddingRelative(50, 10, 50, 10);
        footer1.setPaddingRelative(50, 10, 0, 10);
        footer2.setPaddingRelative(50, 10, 50, 10);
        footer.addView(footer0, 0);
        footer.addView(footer1, 1);
        footer.addView(footer2, 2);
        salaryDataTable.addView(footer, id);
    }
}