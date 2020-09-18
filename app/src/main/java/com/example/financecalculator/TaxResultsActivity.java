package com.example.financecalculator;

import android.os.Bundle;

import com.example.financecalculator.ui.TaxResults.PageViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.example.financecalculator.ui.TaxResults.SectionsPagerAdapter;
import com.example.financecalculator.ui.TaxResults.PageViewModel.*;

public class TaxResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_results);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null){
//            if (bundle.get("Salary") != null){
//                System.out.println("Salary: "+bundle.get("Salary"));
//                pageViewModel.setSalary((Integer) bundle.get("Salary"));
//            }
//        }
    }
}