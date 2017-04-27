package com.example.bittu.starbuzz;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkCategoryActivity extends ListActivity{

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView=getListView();


        try{

            SQLiteOpenHelper starbuzz=new StarbuzzDatabaseHelper(this);
            db=starbuzz.getReadableDatabase();

            cursor=db.query("drink",
                            new String[]{"_id","name"},
                            null,null,null,null,null);

            CursorAdapter listAdaptor=new SimpleCursorAdapter(this,
                                            android.R.layout.simple_list_item_1,
                                            cursor,
                                            new String[]{"name"},
                                            new int[]{android.R.id.text1},
                                            0);

            listView.setAdapter(listAdaptor);



        }catch (SQLiteException e){
            Toast.makeText(this,"Databse unavailble",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    public void onListItemClick(ListView listView, View itemView, int position, long id)
    {
        Intent intent=new Intent(DrinkCategoryActivity.this,DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO,(int) id);
        startActivity(intent);
    }
}
