package com.example.financecalculator.ui.TaxCalculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.financecalculator.R;
import com.example.financecalculator.TaxResultsActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

public class TaxFragment extends Fragment {

    private TaxViewModel taxViewModel;
    private Integer salaryInt;
    private Integer value_80C;
    private Integer value_80CC;
    private Integer value_VIA;
    private Integer value_hra;
    private Integer value_home_loan;
    private Integer value_edu_loan;
    private Boolean regime;
    private TableLayout salaryDataTable;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        final View root = inflater.inflate(R.layout.fragment_tax, container, false);
        final Button submitButton = root.findViewById(R.id.button);
        final TextView salaryValue = root.findViewById(R.id.salaryDecimal);
        final TextView deductions_80C = root.findViewById(R.id.field_80c);
        final TextView deductions_80CC = root.findViewById(R.id.field_80cc);
        final TextView deductions_VIA = root.findViewById(R.id.field_hip);
        final TextView deductions_hra = root.findViewById(R.id.field_hra);
        final TextView deductions_home_loan = root.findViewById(R.id.field_home_loan);
        final TextView deductions_edu_loan = root.findViewById(R.id.field_edu_loan);
        taxViewModel = ViewModelProviders.of(this).get(TaxViewModel.class);
        final int max_80C = 150000;
        final int max_80CC = 50000;
        final int max_VIA = Integer.MAX_VALUE;
        final int max_hra = Integer.MAX_VALUE;
        final int max_home_loan = Integer.MAX_VALUE;
        final int max_edu_loan = Integer.MAX_VALUE;

        final View popupViewLayout = inflater.inflate(R.layout.error_popup, container, false);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                View hideView = requireActivity().getCurrentFocus();
                if (hideView != null) {
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupViewLayout, width, height, focusable);
                final TextView popupText = popupViewLayout.findViewById(R.id.error_popup_dialog);
                final Button popupClose = popupViewLayout.findViewById(R.id.popUpCloseButton);
                popupClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                salaryInt = 0;
                value_80C = 0;
                value_80CC = 0;
                value_VIA = 0;
                value_hra = 0;
                value_home_loan = 0;
                value_edu_loan = 0;
                boolean popUp = false;
                String temp = salaryValue.getText().toString();
                String text = popupText.getText().toString();
                if (!temp.isEmpty()){
                    salaryInt = Integer.valueOf(temp);
                }
                temp = deductions_80C.getText().toString();
                if (!temp.isEmpty()) {
                    value_80C = Integer.valueOf(temp);
                    if (value_80C > max_80C){
                        text = String.join(text, String.format("Max Allowed deductions in 80C: %d\n", max_80C));
                        popUp = true;
                    }
                }
                temp = deductions_80CC.getText().toString();
                if (!temp.isEmpty()) {
                    value_80CC = Integer.valueOf(temp);
                    if (value_80CC > max_80CC){
                        text = String.join(text, String.format("Max Allowed deductions in 80CC: %d\n", max_80CC));
                        popUp = true;
                    }
                }
                temp = deductions_VIA.getText().toString();
                if (!temp.isEmpty()) {
                    value_VIA = Integer.valueOf(temp);
                }
                temp = deductions_hra.getText().toString();
                if (!temp.isEmpty()) {
                    value_hra = Integer.valueOf(temp);
                }
                temp = deductions_home_loan.getText().toString();
                if (!temp.isEmpty()) {
                    value_home_loan = Integer.valueOf(temp);
                }
                temp = deductions_edu_loan.getText().toString();
                if (!temp.isEmpty()) {
                    value_edu_loan = Integer.valueOf(temp);
                }

                if (popUp){
                    System.out.println("Error Text " + text);
                    popupText.setText(text);
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                } else {
                    Intent taxResultsIntent = new Intent(requireActivity(), TaxResultsActivity.class);
                    taxResultsIntent.putExtra("Salary", salaryInt);
                    taxResultsIntent.putExtra("value_80C", value_80C);
                    taxResultsIntent.putExtra("value_80CC", value_80CC);
                    taxResultsIntent.putExtra("value_hra", value_hra);
                    taxResultsIntent.putExtra("value_home_loan", value_home_loan);
                    taxResultsIntent.putExtra("value_edu_loan", value_edu_loan);
                    taxResultsIntent.putExtra("value_VIA", value_VIA);
                    startActivity(taxResultsIntent);
                }
            }
        });

/*        Spinner spinner = root.findViewById(R.id.investment_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.investments_array, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/

        return root;
    }
}