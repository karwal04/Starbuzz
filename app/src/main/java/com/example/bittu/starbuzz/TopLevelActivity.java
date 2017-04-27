package com.example.bittu.starbuzz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    if(position==0){
                        Intent intent=new Intent(TopLevelActivity.this,DrinkCategoryActivity.class);
                        startActivity(intent);
                    }
            }
        };

        ListView listView=(ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(itemClickListener);




        ListView list_fav=(ListView)findViewById(R.id.fav);

        try{
            SQLiteOpenHelper starbuzz=new StarbuzzDatabaseHelper(this);
            db=starbuzz.getReadableDatabase();

            fav=db.query("drink",
                        new String[]{"_id","name"},
                        "fav=1",
                        null,null,null,null);

            CursorAdapter favAdaptor=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,fav,new String[]{"name"},new int[]{android.R.id.text1},0);

            list_fav.setAdapter(favAdaptor);



        }catch (SQLiteException e){
            Toast.makeText(this,"database unavailable",Toast.LENGTH_SHORT).show();
        }


        list_fav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(TopLevelActivity.this,DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKNO,(int)l);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fav.close();
        db.close();
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        try {

            StarbuzzDatabaseHelper starbuzzDatabaseHelper=new StarbuzzDatabaseHelper(this);
            db=starbuzzDatabaseHelper.getReadableDatabase();
            Cursor newCursor=db.query("drink",
                                        new String[]{"_id","name"},
                                        "fav=1",
                                        null,null,null,null);

            ListView listfav=(ListView)findViewById(R.id.fav);
            CursorAdapter adaptor=(CursorAdapter)listfav.getAdapter();
            adaptor.changeCursor(newCursor);
            fav=newCursor;

        }catch (SQLiteException e){
            Toast.makeText(this,"database unavailable",Toast.LENGTH_SHORT).show();
        }

    }
}
