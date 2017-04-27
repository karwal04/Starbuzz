package com.example.bittu.starbuzz;

/**
 * Created by Bittu on 05-03-2017.
 */

public class Drink {
    private String name;
    private String description;
    private int imgaeResourceId;

    public static final Drink[] drinks={
            new Drink("LATTE","A couple of hot milk with espresso shots",R.drawable.latte),
            new Drink("FILTER","A couple of hot milk with espresso shots",R.drawable.filter)
    };

    private Drink(String name,String description,int imageResourceId)
    {
        this.name=name;
        this.description=description;
        this.imgaeResourceId=imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImgaeResourceId() {
        return imgaeResourceId;
    }

    public String toString()
    {
        return this.name;
    }
}
