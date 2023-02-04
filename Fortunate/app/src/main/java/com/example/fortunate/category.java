package com.example.fortunate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class category extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    String username;
    TextView lbluser;
    LinearLayout l;
    EditText et;
    Button btnDel, btnRenm, btnAdd, btnSave;
    String[] catTitles;
    double[] catAmount;
    int[] catCount;
    public static FileOutputStream fos;
    BottomNavigationView bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        username = getIntent().getExtras().getString("User");
        String[] categories = DataBase.getRow(username,signup.fis)[3].split(",");
        Categories cat = new Categories(categories);

        this.catTitles = cat.getTitle();
        this.catAmount = cat.getAmount();
        this.catCount = cat.getCount();

        lbluser = (TextView) findViewById(R.id.lbl_username);
        lbluser.setText(username);
        bar = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bar.setOnNavigationItemSelectedListener(this);

        draw();
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(catTitles.length < 10){
                    add();
                }
                else{
                    Context context = getApplicationContext();
                    CharSequence text = "You can only have 10 categories";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    public void draw(){
        for (int i=1; i<11; i++){
            String id = "line" + i;
            int resID = this.getResources().getIdentifier(id, "id", this.getPackageName());
            l = (LinearLayout) findViewById(resID);
            l.setVisibility(View.GONE);
        }

        for (int i =0; i < this.catTitles.length; i++){
            String id = "line" + (i+1);
            String idCat = "cat" + (i+1);
            String idDel = "btn_del" + (i+1);
            String idRenm = "btn_renm" + (i+1);

            int resID = this.getResources().getIdentifier(id, "id", this.getPackageName());
            l = (LinearLayout) findViewById(resID);
            l.setVisibility(View.VISIBLE);

            int resIDCat = this.getResources().getIdentifier(idCat, "id", this.getPackageName());
            et = (EditText) findViewById(resIDCat);
            et.setText(catTitles[i]);

            int resIDDel = this.getResources().getIdentifier(idDel, "id", this.getPackageName());
            btnDel = (Button) findViewById(resIDDel);
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete(view);
                }
            });

            int resIDRenm = this.getResources().getIdentifier(idRenm, "id", this.getPackageName());
            btnRenm = (Button) findViewById(resIDRenm);
            btnRenm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rename(view);
                }
            });
        }
    }

    public void save(){
        String[] info = DataBase.getRow(username, signup.fis);
        String cat = Categories.getString(this.catTitles,this.catCount,this.catAmount);
        DataBase.Update(info[0],info[1],info[2],cat,info[4],Double.parseDouble(info[5]),Double.parseDouble(info[6]),signup.fis,this);
    }

    public void add(){
        String[] tempTitle = this.catTitles;
        int[] tempCount = this.catCount;
        double[] tempAmount = this.catAmount;

        this.catTitles = new String[tempTitle.length + 1];
        this.catCount = new int[tempCount.length + 1];
        this.catAmount = new double[tempAmount.length + 1];

        for (int i =0; i < tempTitle.length; i++){
            this.catTitles[i] = tempTitle[i];
            this.catCount[i] = tempCount[i];
            this.catAmount[i] = tempAmount[i];
        }
        this.catTitles[tempTitle.length] = "New Category";
        this.catCount[tempTitle.length] = 0;
        this.catAmount[tempTitle.length] = 0.0;
        draw();
    }

    public void rename(View v){
        switch (v.getId()) {
            case R.id.btn_renm1:
                EditText txt = (EditText) findViewById(R.id.cat1);
                this.catTitles[0] = txt.getText().toString();
                break;
            case R.id.btn_renm2:
                EditText txt1 = (EditText) findViewById(R.id.cat2);
                this.catTitles[1] = txt1.getText().toString();
                break;
            case R.id.btn_renm3:
                EditText txt2 = (EditText) findViewById(R.id.cat3);
                this.catTitles[2] = txt2.getText().toString();
                break;
            case R.id.btn_renm4:
                EditText txt3 = (EditText) findViewById(R.id.cat4);
                this.catTitles[3] = txt3.getText().toString();
                break;
            case R.id.btn_renm5:
                EditText txt4 = (EditText) findViewById(R.id.cat5);
                this.catTitles[4] = txt4.getText().toString();
                break;
            case R.id.btn_renm6:
                EditText txt5 = (EditText) findViewById(R.id.cat6);
                this.catTitles[5] = txt5.getText().toString();
                break;
            case R.id.btn_renm7:
                EditText txt6 = (EditText) findViewById(R.id.cat7);
                this.catTitles[6] = txt6.getText().toString();
                break;
            case R.id.btn_renm8:
                EditText txt7 = (EditText) findViewById(R.id.cat8);
                this.catTitles[7] = txt7.getText().toString();
                break;
            case R.id.btn_renm9:
                EditText txt8 = (EditText) findViewById(R.id.cat9);
                this.catTitles[8] = txt8.getText().toString();
                break;
            case R.id.btn_renm10:
                EditText txt9 = (EditText) findViewById(R.id.cat10);
                this.catTitles[9] = txt9.getText().toString();
                break;
        }
    }

    public void delete(View v){
        String[] tempTitle = this.catTitles;
        int[] tempCount = this.catCount;
        double[] tempAmount = this.catAmount;

        this.catTitles = new String[tempTitle.length -1];
        this.catCount = new int[tempCount.length -1];
        this.catAmount = new double[tempAmount.length -1];
        int j = 0;
        switch (v.getId()) {
            case R.id.btn_del1:
                for (int i =0; i < tempTitle.length; i++){
                    if(i!=0){
                        this.catTitles[j] = tempTitle[i];
                        this.catCount[j] = tempCount[i];
                        this.catAmount[j] = tempAmount[i];
                        j += 1;
                    }
                }
                break;
            case R.id.btn_del2:
                for (int i =0; i < tempTitle.length; i++){
                    if(i!=1){
                        this.catTitles[j] = tempTitle[i];
                        this.catCount[j] = tempCount[i];
                        this.catAmount[j] = tempAmount[i];
                        j += 1;
                    }
                }
                break;
            case R.id.btn_del3:
                for (int i =0; i < tempTitle.length; i++){
                    if(i!=2){
                        this.catTitles[j] = tempTitle[i];
                        this.catCount[j] = tempCount[i];
                        this.catAmount[j] = tempAmount[i];
                        j += 1;
                    }
                }
                break;
            case R.id.btn_del4:
                for (int i =0; i < tempTitle.length; i++){
                    if(i!=3){
                        this.catTitles[j] = tempTitle[i];
                        this.catCount[j] = tempCount[i];
                        this.catAmount[j] = tempAmount[i];
                        j += 1;
                    }
                }
                break;
            case R.id.btn_del5:
                for (int i =0; i < tempTitle.length; i++){
                    if(i!=4){
                        this.catTitles[j] = tempTitle[i];
                        this.catCount[j] = tempCount[i];
                        this.catAmount[j] = tempAmount[i];
                        j += 1;
                    }
                }
                break;
            case R.id.btn_del6:
                for (int i =0; i < tempTitle.length; i++){
                    if(i!=5){
                        this.catTitles[j] = tempTitle[i];
                        this.catCount[j] = tempCount[i];
                        this.catAmount[j] = tempAmount[i];
                        j += 1;
                    }
                }
                break;
            case R.id.btn_del7:
                for (int i =0; i < tempTitle.length; i++){
                    if(i!=6){
                        this.catTitles[j] = tempTitle[i];
                        this.catCount[j] = tempCount[i];
                        this.catAmount[j] = tempAmount[i];
                        j += 1;
                    }
                }
                break;
            case R.id.btn_del8:
                for (int i =0; i < tempTitle.length; i++){
                    if(i!=7){
                        this.catTitles[j] = tempTitle[i];
                        this.catCount[j] = tempCount[i];
                        this.catAmount[j] = tempAmount[i];
                        j += 1;
                    }
                }
                break;
            case R.id.btn_del9:
                for (int i =0; i < tempTitle.length; i++){
                    if(i!=8){
                        this.catTitles[j] = tempTitle[i];
                        this.catCount[j] = tempCount[i];
                        this.catAmount[j] = tempAmount[i];
                        j += 1;
                    }
                }
                break;
            case R.id.btn_del10:
                for (int i =0; i < tempTitle.length; i++){
                    if(i!=9){
                        this.catTitles[j] = tempTitle[i];
                        this.catCount[j] = tempCount[i];
                        this.catAmount[j] = tempAmount[i];
                        j += 1;
                    }
                }
                break;
        }
        draw();
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