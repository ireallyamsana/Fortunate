package com.example.fortunate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class stats extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    String username;
    TextView lbluser;
    BottomNavigationView bar;
    Button details;
    String[] catTitles;
    double[] catAmount;
    int[] catCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        username = getIntent().getExtras().getString("User");
        lbluser = (TextView) findViewById(R.id.lbl_username);
        lbluser.setText(username);

        details = (Button) findViewById(R.id.btn_details);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hist();
            }
        });

        bar = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bar.setOnNavigationItemSelectedListener(this);

        String[] categories = DataBase.getRow(username,signup.fis)[3].split(",");
        Categories cat = new Categories(categories);

        this.catTitles = cat.getTitle();
        this.catAmount = cat.getAmount();
        this.catCount = cat.getCount();

        PieChart pieChart = findViewById(R.id.pieChart);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setCenterText("Expenditure by Category");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setEnabled(false);

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i =0; i<catTitles.length; i++){
            entries.add(new PieEntry((float) catAmount[i], catTitles[i]));
        }

        PieDataSet dataset = new PieDataSet(entries,"");
        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.rgb(20, 99, 165));
        colors.add(Color.rgb(0,191,255));
        colors.add(Color.rgb(65,105,225));
        colors.add(Color.rgb(0,0,205));
        colors.add(Color.rgb(0,139,139));
        colors.add(Color.rgb(0,255,255));
        colors.add(Color.rgb(47,79,79));
        colors.add(Color.rgb(0,100,0));
        colors.add(Color.rgb(70,130,180));
        colors.add(Color.rgb(100,149,237));


        PieData data = new PieData(dataset);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        dataset.setColors(colors);
        pieChart.animateXY(5000, 5000);
    }

    public void hist(){
        Intent intent = new Intent(this, details.class);
        intent.putExtra("User",username);
        startActivity(intent);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cat:
                Intent intent = new Intent(this, category.class);
                intent.putExtra("User",username);
                startActivity(intent);
                return true;

            case R.id.home:
                Intent intent2 = new Intent(this, MainPage.class);
                intent2.putExtra("User",username);
                startActivity(intent2);
                return true;
            case R.id.stats:
                Intent intent3 = new Intent(this, stats.class);
                intent3.putExtra("User",username);
                startActivity(intent3);
                return true;

            default:
                return true;
        }
    }
}