package com.example.fortunate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class signin extends AppCompatActivity {
    EditText user,pass;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        user = (EditText) findViewById(R.id.lbl_Username);
        pass = (EditText) findViewById(R.id.lbl_Pass);
        btn = (Button) findViewById(R.id.btn_signIn2);

        if (signup.didSignUp == false){
            try
            {
                signup.fis = new FileInputStream(new File(getFilesDir(), "abc.txt"));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] info = DataBase.getRow(user.getText().toString(), signup.fis);
                if (info[2].equals(pass.getText().toString())) {
                    openMain();
                }

            }
        });
    }
    public void openMain(){
        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra("User",user.getText().toString());
        startActivity(intent);
    }
}