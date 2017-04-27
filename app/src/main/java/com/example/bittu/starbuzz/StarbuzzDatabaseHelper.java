package com.example.bittu.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bittu on 25-03-2017.
 */

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {
    private  static final String DB_NAME="starbuzz";
    private static final int DB_VERSION=2;

    StarbuzzDatabaseHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db,0,DB_VERSION);
    }

    private void insertDrink(SQLiteDatabase db,String name,String description,int resourceId){
        ContentValues drinkvalues=new ContentValues();
        drinkvalues.put("name",name);
        drinkvalues.put("description",description);
        drinkvalues.put("image_resource_id",resourceId);
        db.insert("drink",null,drinkvalues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int olderVersion, int newVersion) {
        updateMyDatabase(sqLiteDatabase,olderVersion,newVersion);
    }


    private void updateMyDatabase(SQLiteDatabase db, int oldversion,int newVersion){
        if(oldversion<1){
            db.execSQL("Create table drink (_id integer primary key autoincrement,"
                    +"name text,"
                    +"description text,"
                    +"image_resource_id integer);");
            insertDrink(db,"Latte","Espresso and stemed milk",R.drawable.latte);
            insertDrink(db,"Filter","filtered coffee with hot milk cream",R.drawable.filter);
        }
        if(oldversion<2){
            db.execSQL("alter table drink add column fav numeric;");
        }

    }
}
