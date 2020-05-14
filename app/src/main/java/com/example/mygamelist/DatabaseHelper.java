package com.example.mygamelist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "MyGameList";
        public static final String TABLE_RATES = "rates";

        public static final String KEY_ID = "_id";
        public static final String KEY_RATE = "rate";

        public DatabaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_RATES + "(" + KEY_ID + " integer primary key," + KEY_RATE + " integer)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_RATES);
            onCreate(db);
        }
}