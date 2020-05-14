package com.example.mygamelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.NumberPicker;
import android.app.Dialog;
import android.util.Log;
import android.content.ContentValues;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    public static ArrayList<String> arrRate = new ArrayList<>(100);
    ViewPager pager;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        initializeArrRate();

        while (fetchData.done != true) {}

        pager = (ViewPager) findViewById(R.id.pagerList);
        pager.setAdapter(new CustomPagerAdapter(this, fetchData.arrPic, fetchData.arrText, fetchData.arrName, arrRate));
        pager.setVisibility(View.VISIBLE);
        pager.setCurrentItem(0);
    }

    public void onRateClicked(View view) {
        show();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is",""+newVal);
    }

    public void show() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("NumberPicker");
        dialog.setContentView(R.layout.dialog);
        Button button_set = (Button) dialog.findViewById(R.id.button_set);
        Button button_cancel = (Button) dialog.findViewById(R.id.button_cancel);
        final NumberPicker np = (NumberPicker) dialog.findViewById(R.id.numberPicker);
        np.setMaxValue(10);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        button_set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.KEY_RATE, String.valueOf(np.getValue()));
                database.update(DatabaseHelper.TABLE_RATES, contentValues, "_id = ?",
                        new String[] { String.valueOf(pager.getCurrentItem() + 1) });

                Cursor cursor = database.query(DatabaseHelper.TABLE_RATES, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DatabaseHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DatabaseHelper.KEY_RATE);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", rate = " + cursor.getString(nameIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();
                dbHelper.close();

                Toast toast = Toast.makeText(getApplicationContext(),"This game is rated!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
                dialog.dismiss();
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void initializeArrRate() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DatabaseHelper.TABLE_RATES, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int rateIndex = cursor.getColumnIndex(DatabaseHelper.KEY_RATE);
            do {
                arrRate.add(cursor.getString(rateIndex));
            } while (cursor.moveToNext());
        } else
            Log.d("mLog","0 rows");

        cursor.close();
    }
}
