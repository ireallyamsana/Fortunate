package com.example.fortunate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class details extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    String username, hist;
    TextView lbluser, lblhist;
    BottomNavigationView bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        username = getIntent().getExtras().getString("User");
        lbluser = (TextView) findViewById(R.id.lbl_username);
        lbluser.setText(username);

        bar = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bar.setOnNavigationItemSelectedListener(this);

        String[] info = DataBase.getRow(username, signup.fis);
        String[] history = info[4].split(",");
        hist = "";
        for(int i =0; i<history.length; i++){
            hist += history[i];
            hist += "\n";
            hist += "\n";
        }

        lblhist = (TextView) findViewById(R.id.lbl_hist);
        lblhist.setText(hist);
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