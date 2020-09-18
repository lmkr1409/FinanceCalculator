package com.example.financecalculator.ui.EMI;

import androidx.annotation.RequiresApi;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.financecalculator.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EMIFragment extends Fragment {

    private EMIViewModel emiViewModel;
    private TableLayout emiTableLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_emi, container, false);
        // Get the elements
        emiViewModel = new EMIViewModel();
        final TextView loan_amount_view = root.findViewById(R.id.emi_loan_amount);
        final TextView interest_view = root.findViewById(R.id.emi_interest);
        final TextView tenure_view = root.findViewById(R.id.emi_tenure);
        final Spinner tenure_mode_view = root.findViewById(R.id.emi_tenure_mode);
        final Button submit = root.findViewById(R.id.emi_submit);
        emiTableLayout = root.findViewById(R.id.emi_result_table);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.emi_tenure_mode,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tenure_mode_view.setAdapter(adapter);
        // Calculate EMI based on Input
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                View hideView = requireActivity().getCurrentFocus();
                if (hideView != null) {
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                int loan = emiViewModel.ReadInputStringToInteger(loan_amount_view.getText().toString());
                float interest = emiViewModel.ReadInputStringToFloat(interest_view.getText().toString());
                int tenure = emiViewModel.ReadInputStringToInteger(tenure_view.getText().toString());
                String mode = tenure_mode_view.getSelectedItem().toString();
                emiViewModel.CalculateEMI(loan, interest, tenure, mode);
                System.out.println(emiViewModel.getEmi());
                loadDataIntoTable(emiViewModel.getTableData());
                for (String[] obj : emiViewModel.getTableData()) {
                    System.out.println(Arrays.toString(obj));
                }
            }
        });

        return root;
    }

    private TableRow MakeTableRow(List<String> data, int textColor, int backgroundColor, int[] rowPadding, int[] colPadding) {
        TableRow row = new TableRow(emiTableLayout.getContext());
        row.setPaddingRelative(rowPadding[0], rowPadding[1], rowPadding[2], rowPadding[3]);
        for (String rowData : data) {
            TextView eachColumn = new TextView(row.getContext());
            eachColumn.setTextColor(textColor);
            eachColumn.setBackgroundColor(backgroundColor);
            eachColumn.setText(rowData);
            eachColumn.setPaddingRelative(colPadding[0], colPadding[1], colPadding[2], colPadding[3]);
            row.addView(eachColumn);
        }
        return row;
    }

    private void loadDataIntoTable(List<String[]> tableData) {
        emiTableLayout.removeAllViews();
        int idx = 0;
        final TableRow header = MakeTableRow(new ArrayList<>(Arrays.asList("Month", "Opening", "Principal", "Interest", "EMI", "Closing", "Paid to Date", "Paid Percentage")),
                Color.WHITE,
                Color.parseColor("#0a318c"),
                new int[]{50, 10, 50, 10},
                new int[]{20, 10, 20, 10});
        emiTableLayout.addView(header, idx++);
        for (String[] rowData : tableData) {
            int bgColor = Color.WHITE;
            int[] colPadding = new int[]{20, 10, 20, 10};
            if (idx % 2 == 0) {
                bgColor = Color.parseColor("#dff7f7");
            }
            if (idx == tableData.size()){
                colPadding = new int[]{20, 10, 20, 10};
            }
            final TableRow row = MakeTableRow(Arrays.asList(rowData),
                    Color.BLACK,
                    bgColor,
                    new int[]{50, 10, 50, 10},
                    colPadding);
            emiTableLayout.addView(row, idx++);
        }
    }

    private void loadDataIntoTable1(List<String[]> tableData) {
        emiTableLayout.removeAllViews();
//        Table Header
        final TableRow header = new TableRow(emiTableLayout.getContext());
        header.setPaddingRelative(30, 10, 30, 10);
        TextView header1 = new TextView(header.getContext());
        TextView header2 = new TextView(header.getContext());
        TextView header3 = new TextView(header.getContext());
        TextView header4 = new TextView(header.getContext());
        TextView header5 = new TextView(header.getContext());
        TextView header6 = new TextView(header.getContext());
        header1.setTextColor(Color.WHITE);
        header2.setTextColor(Color.WHITE);
        header3.setTextColor(Color.WHITE);
        header4.setTextColor(Color.WHITE);
        header5.setTextColor(Color.WHITE);
        header6.setTextColor(Color.WHITE);
        header1.setBackgroundColor(Color.parseColor("#0a318c"));// RBG (10,49,140)
        header2.setBackgroundColor(Color.parseColor("#0a318c"));
        header3.setBackgroundColor(Color.parseColor("#0a318c"));
        header4.setBackgroundColor(Color.parseColor("#0a318c"));
        header5.setBackgroundColor(Color.parseColor("#0a318c"));
        header6.setBackgroundColor(Color.parseColor("#0a318c"));
        header1.setText("Month");
        header2.setText("Opening");
        header3.setText("Principal");
        header4.setText("Interest");
        header5.setText("EMI");
        header6.setText("Closing");
        header1.setPaddingRelative(20, 10, 0, 10);
        header2.setPaddingRelative(20, 10, 0, 10);
        header3.setPaddingRelative(20, 10, 0, 10);
        header4.setPaddingRelative(20, 10, 0, 10);
        header5.setPaddingRelative(20, 10, 0, 10);
        header6.setPaddingRelative(20, 10, 20, 10);
        header.addView(header1, 0);
        header.addView(header2, 1);
        header.addView(header3, 2);
        header.addView(header4, 3);
        header.addView(header5, 4);
        header.addView(header6, 5);
        emiTableLayout.addView(header, 0);
//        Table Data
        int id = 1;
        for (String[] rowData : tableData) {
            final TableRow tr = new TableRow(emiTableLayout.getContext());
            tr.setPaddingRelative(30, 10, 30, 10);
            TextView col1 = new TextView(tr.getContext());
            TextView col2 = new TextView(tr.getContext());
            TextView col3 = new TextView(tr.getContext());
            TextView col4 = new TextView(tr.getContext());
            TextView col5 = new TextView(tr.getContext());
            TextView col6 = new TextView(tr.getContext());
            col1.setTextColor(Color.BLACK);
            col2.setTextColor(Color.BLACK);
            col3.setTextColor(Color.BLACK);
            col4.setTextColor(Color.BLACK);
            col5.setTextColor(Color.BLACK);
            col6.setTextColor(Color.BLACK);
            if (id % 2 == 1) {
                col1.setBackgroundColor(Color.WHITE);
                col2.setBackgroundColor(Color.WHITE);
                col3.setBackgroundColor(Color.WHITE);
                col4.setBackgroundColor(Color.WHITE);
                col5.setBackgroundColor(Color.WHITE);
                col6.setBackgroundColor(Color.WHITE);
            } else {
                col1.setBackgroundColor(Color.parseColor("#dff7f7")); // RGB (223,247,247)
                col2.setBackgroundColor(Color.parseColor("#dff7f7"));
                col3.setBackgroundColor(Color.parseColor("#dff7f7"));
                col4.setBackgroundColor(Color.parseColor("#dff7f7"));
                col5.setBackgroundColor(Color.parseColor("#dff7f7"));
                col6.setBackgroundColor(Color.parseColor("#dff7f7"));
            }
            col1.setText(rowData[0]);
            col2.setText(rowData[1]);
            col3.setText(rowData[2]);
            col4.setText(rowData[3]);
            col5.setText(rowData[4]);
            col6.setText(rowData[5]);
            col1.setPaddingRelative(20, 10, 0, 10);
            col2.setPaddingRelative(20, 10, 0, 10);
            col3.setPaddingRelative(20, 10, 0, 10);
            col4.setPaddingRelative(20, 10, 0, 10);
            col5.setPaddingRelative(20, 10, 0, 10);
            col6.setPaddingRelative(20, 10, 20, 10);
            tr.addView(col1, 0);
            tr.addView(col2, 1);
            tr.addView(col3, 2);
            tr.addView(col4, 3);
            tr.addView(col5, 4);
            tr.addView(col6, 5);
            emiTableLayout.addView(tr, id++);
        }
    }
}