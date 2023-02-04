package com.example.fortunate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class signup extends AppCompatActivity {
    EditText user,email,pass,confirm;
    Button btn;
    public static FileInputStream fis;
    public static boolean didSignUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user = (EditText) findViewById(R.id.lbl_User);
        email = (EditText) findViewById(R.id.lbl_Email);
        pass = (EditText) findViewById(R.id.lbl_Pass2);
        confirm = (EditText) findViewById(R.id.lbl_ConfirmPass);
        btn = (Button) findViewById(R.id.btn_signUp2);



        try
        {
            fis = new FileInputStream(new File(getFilesDir(), "abc.txt"));
        }
        catch (FileNotFoundException e) {
            try
            {
                FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), "abc.txt"));
            }
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            try
            {
                fis = new FileInputStream(new File(getFilesDir(), "abc.txt"));
            }
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    openMain();

            }
        });
    }

    public void openMain(){
        if(pass.getText().toString().equals(confirm.getText().toString())) {
            DataBase.Signup(user.getText().toString(), email.getText().toString(),
                    pass.getText().toString(),this, fis);
            didSignUp = true;
            Intent intent = new Intent(this, signin.class);
            startActivity(intent);
        }
    }
}