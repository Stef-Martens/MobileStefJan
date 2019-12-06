package com.example.mobilestefjan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

import java.util.Date;


public class DatabankTransacties extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Transacties.db";
    public static final String TABLE_NAME = "transacties_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "BEDRAG";
    public static final String COL_3 = "DATUM";
    public static final String COL_4 = "OMSCHRIJVING";
    //public static final Byte[] COL_5 = "FOTO";

    public DatabankTransacties(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,BEDRAG INTEGER,DATUM STRING,OMSCHRIJVING TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(Integer bedrag, String datum, String omschrijving) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,bedrag);
        contentValues.put(COL_3,datum);
        contentValues.put(COL_4,omschrijving);
        //contentValues.put(COL_5,foto);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DROP TABLE "+TABLE_NAME);
        //onCreate(db);
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String achternaam,String voornaam,String geld) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,achternaam);
        contentValues.put(COL_3,voornaam);
        contentValues.put(COL_4,geld);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});

    }
}