package com.example.fortunate;

import android.widget.LinearLayout;

public class Categories {

    private String[] title;
    private int[] count;
    private double[] amount;

    public Categories(String[] cat){
        this.title = new String[cat.length];
        this.count = new int[cat.length];
        this.amount = new double[cat.length];

        for (int i=0; i<cat.length; i++){
            this.title[i] = cat[i].split(":",0)[0];
            this.amount[i] = Double.parseDouble(cat[i].split(":",0)[1]);
            this.count[i] = Integer.parseInt(cat[i].split(":",0)[2]);
        }
    }

    public String[] getTitle(){
        return this.title;
    }

    public int[] getCount(){
        return this.count;
    }

    public double[] getAmount(){
        return this.amount;
    }

    public static String getString(String[] title, int[] count, double[] amount){
        String output = "";
        for (int i = 0; i < title.length; i++){
            if (i == title.length -1){
                output += title[i]+":"+amount[i]+":"+count[i];
            }
            else {
                output += title[i] + ":" + amount[i] + ":" + count[i] + ",";
            }
        }
        return output;
    }

}
