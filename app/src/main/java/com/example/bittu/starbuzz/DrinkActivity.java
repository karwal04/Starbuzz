package com.example.bittu.starbuzz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKNO="drink no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkno=(Integer) getIntent().getExtras().get(EXTRA_DRINKNO);

        try {
            SQLiteOpenHelper starbuzzDatabaseHelper=new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db=starbuzzDatabaseHelper.getReadableDatabase();

            Cursor cursor=db.query("drink",
                                    new String[]{"name","description","image_resource_id","fav"},
                                    "_id=?",
                                    new String[]{Integer.toString(drinkno)},
                                    null,null,null);

            if(cursor.moveToFirst()){
                String nameText=cursor.getString(0);
                String desc=cursor.getString(1);
                int photoId=cursor.getInt(2);
                boolean isfav=(cursor.getInt(3)==1);


                ImageView imageView=(ImageView)findViewById(R.id.photo);
                imageView.setImageResource(photoId);

                TextView textView=(TextView) findViewById(R.id.drink_name);
                textView.setText(nameText);

                TextView description=(TextView) findViewById(R.id.drink_description);
                description.setText(desc);

                CheckBox fav=(CheckBox) findViewById(R.id.fav);
                fav.setChecked(isfav);


            }
            cursor.close();
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(this,"Databse unavaliable",Toast.LENGTH_SHORT).show();
        }

    }

    public void onFavClicked(View view){
        int drinkNo=(Integer)getIntent().getExtras().get(EXTRA_DRINKNO);
        CheckBox fav=(CheckBox)findViewById(R.id.fav);
        ContentValues drinkvalues=new ContentValues();
        drinkvalues.put("fav",fav.isChecked());
        SQLiteOpenHelper starbuzz=new StarbuzzDatabaseHelper(this);

        try {
            SQLiteDatabase db=starbuzz.getWritableDatabase();
            db.update("drink",drinkvalues,"_id=?",new String[]{Integer.toString(drinkNo)});
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(this,"Database unavailble",Toast.LENGTH_SHORT).show();
        }
    }







}
