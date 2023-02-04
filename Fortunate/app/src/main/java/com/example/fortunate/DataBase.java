package com.example.fortunate;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class DataBase {

    private static Context context;
    public static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DataBase";
    String username;
    private static FileOutputStream fos;


    public DataBase(String username){
        this.username = username;
    }

    public static void Write(String[] data, FileOutputStream fos){

        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }

    }

    public static void Signup(String username,String email,String pass, signup obj, FileInputStream fis){
        String[] data = getData(username,email,pass,"","",0,0,fis,false);
        try
        {
            fos = new FileOutputStream(new File(obj.getFilesDir(), "abc.txt"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Write(data, fos);
    }


    public static String[] getRow(String username, FileInputStream fis){

        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                anzahl++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        ArrayList<String> usersInfo = new ArrayList<String>();

        String line;
        int i = 0;
        try
        {
            String content = "";
            while((line=br.readLine())!=null)
            {
                if(line.equals("--next--")){
                    usersInfo.add(content);
                    content = "";
                }
                else {
                    content += line;
                }
            }
        }
        catch (IOException e) {e.printStackTrace();}

        String[] info = new String[7];

        for (int j=0; j<usersInfo.size(); j++){
            if (usersInfo.get(j).split(";",0)[0].equals(username)){
                info = usersInfo.get(j).split(";",0);
                break;
            }
        }
        return info;
    }

    public static void Update(String user, String email, String pass, String cat, String hist, double exp, double limit, FileInputStream fis, category obj){
      String[] data = getData(user,email,pass,cat,hist,exp,limit,fis,true);
        try
        {
            fos = new FileOutputStream(new File(obj.getFilesDir(), "abc.txt"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Write(data, fos);
    }

    public static void Update(String user, String email, String pass, String cat, String hist, double exp, double limit, FileInputStream fis, AddExpenditure obj){
        String[] data = getData(user,email,pass,cat,hist,exp,limit,fis,true);
        try
        {
            fos = new FileOutputStream(new File(obj.getFilesDir(), "abc.txt"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Write(data, fos);
    }

    public static void Update(String user, String email, String pass, String cat, String hist, double exp, double limit, FileInputStream fis, MainPage obj){
        String[] data = getData(user,email,pass,cat,hist,exp,limit,fis,true);
        try
        {
            fos = new FileOutputStream(new File(obj.getFilesDir(), "abc.txt"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Write(data, fos);
    }

    public static String[] getData(String user, String email, String pass, String cat, String hist, double exp, double limit,FileInputStream fis, boolean isUpdate){
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                anzahl++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        ArrayList<String[]> usersInfo = new ArrayList<String[]>();

        String line;
        int i = 0;
        try
        {
            String[] content = new String[8];
            while((line=br.readLine())!=null)
            {
                if(line.equals("--next--")){
                    content[i] = line;
                    usersInfo.add(content);
                    content = new String[8];
                    i = 0;
                }
                else {
                    content[i] = line;
                    i++;
                }
            }
        }
        catch (IOException e) {e.printStackTrace();}

        if(isUpdate == true) {
            for (int j = 0; j < usersInfo.size(); j++) {
                if (usersInfo.get(j)[0].split(";")[0].equals(user)) {
                    String[] data = new String[]{user + ";",
                            email + ";",
                            pass + ";",
                            cat + ";",
                            hist + ";",
                            exp + ";",
                            String.valueOf(limit),
                            "--next--"};
                    usersInfo.add(j, data);
                    usersInfo.remove(j + 1);
                    break;
                }
            }
            String[] data = new String[usersInfo.size()*8];
            int counter = 0;
            for (int k = 0; k<usersInfo.size(); k++ ){
                for (int j = 0; j < usersInfo.get(k).length; j++){
                    data[counter] = usersInfo.get(k)[j];
                    counter++;
                }
            }
            return data;
        }
        else {
            String[] data = new String[(usersInfo.size()+1)*8];
            int counter = 0;
            for (int k = 0; k<usersInfo.size(); k++ ){
                for (int j = 0; j < usersInfo.get(k).length; j++){
                    data[counter] = usersInfo.get(k)[j];
                    counter++;
                }
            }
            String[] newData = new String[]{user+";",
                    email+";",
                    pass+";",
                    "Grocery:0.0:0,Food:0.0:0,Rent:0.0:0;",
                    ";",
                    "0.0;",
                    "0.0",
                    "--next--"};
            for (int j = 0; j < 8; j++){
                data[counter+j] = newData[j];
            }
            return data;
        }

    }

}
