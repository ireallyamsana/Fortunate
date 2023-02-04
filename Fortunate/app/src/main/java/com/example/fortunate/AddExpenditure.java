package com.example.fortunate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddExpenditure extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    String username;
    TextView lbluser;
    Spinner SPoperation, SPcategory;
    Button submit;
    BottomNavigationView bar;
    String[] cats;
    EditText amount, date;
    double[] catAmount;
    int[] catCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenditure);
        username = getIntent().getExtras().getString("User");
        lbluser = (TextView) findViewById(R.id.lbl_username);
        lbluser.setText(username);

        String[] op = new String[]{"Add", "Subtract"};
        String[] categories = DataBase.getRow(username,signup.fis)[3].split(",");
        Categories cat = new Categories(categories);
        cats = cat.getTitle();
        catAmount = cat.getAmount();
        catCount = cat.getCount();

        ArrayAdapter<String> OPadapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,op);

        ArrayAdapter<String> Catadapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,cats);

        amount = (EditText) findViewById(R.id.lbl_Amount);

        date = (EditText) findViewById(R.id.editTextDate);

        bar = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bar.setOnNavigationItemSelectedListener(this);

        SPoperation = (Spinner) findViewById(R.id.SPop);
        OPadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPoperation.setAdapter(OPadapter);

        SPcategory = (Spinner) findViewById(R.id.SPcat);
        Catadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPcategory.setAdapter(Catadapter);

        submit = (Button) findViewById(R.id.btn_details);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

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

    public void submit(){
        String Hist = "";
        double exp = 0.0;
        String pass = "";
        String email = "";
        double limit = 0;
        String selectedOperation = SPoperation.getSelectedItem().toString();
        String selectedCat = SPcategory.getSelectedItem().toString();
        double amount = Double.parseDouble(this.amount.getText().toString());
        String[] info = DataBase.getRow(username, signup.fis);

        email = info[1];
        pass = info[2];
        Hist = info[4];
        exp = Double.parseDouble(info[5]);
        limit = Double.parseDouble(info[6]);


        for (int i =0; i < cats.length; i++){
            if(cats[i] == selectedCat){
                if (selectedOperation.equals("Add")){
                    double avg = catAmount[i]/catCount[i];
                    if(exp +amount > limit){
                        Context context = getApplicationContext();
                        CharSequence text = "The entered amount cannot be added, your expenditure exceeded the limit!";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        break;
                    }
                    else if (amount > avg){
                        Context context = getApplicationContext();
                        CharSequence text = "The entered amount is higher than your average expenditure!";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        catAmount[i] += amount;
                        exp += amount;
                        catCount[i] += 1;
                        Hist += "Added: " + cats[i] + " " + amount + " " + date.getText().toString() + ",";
                        break;
                    }
                    else {
                        catAmount[i] += amount;
                        exp += amount;
                        catCount[i] += 1;
                        Hist += "Added: " + cats[i] + " " + amount + " " + date.getText().toString() + ",";
                        break;
                    }
                }
                else {
                    catAmount[i] -= amount;
                    exp -= amount;
                    Hist += "Subtracted: " + cats[i] + " " + amount + " " + date.getText().toString() + ",";
                    break;
                }
            }
        }
        String cat = Categories.getString(cats,catCount,catAmount);

        DataBase.Update(this.username,email,pass,cat,Hist,exp,limit,signup.fis,this);
    }
}