package com.example.lb8;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String MY_TABLE = "ZAMETKI";
    private static final int VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, MY_TABLE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MY_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "RAND_NUMB TEXT NOT NULL," +
                "RAND_TXT INTEGER NOT NULL UNIQUE);");
    }

    public static void insData(SQLiteDatabase db, Integer randNum, String randTxt) {
        ContentValues cv = new ContentValues();
        cv.put("RAND_NUMB", randNum);
        cv.put("RAND_TXT", randTxt);
        db.insert(MY_TABLE, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}