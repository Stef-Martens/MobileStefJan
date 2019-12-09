package com.example.mobilestefjan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

public class DatabankVoorJezelf extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Jezelf.db";
    public static final String TABLE_NAME = "jezelf_table";
    public static final String COL_1 = "ACHTERNAAM";
    public static final String COL_2 = "VOORNAAM";
    public static final String COL_3 = "SALDO";

    public DatabankVoorJezelf(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ACHTERNAAM STRING,VOORNAAM STRING,SALDO STRING)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(String achternaam, String voornaam, String saldo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

//        contentValues.put(COL_2,bedrag);
//        contentValues.put(COL_3,datum);
//        contentValues.put(COL_4,omschrijving);
//        contentValues.put(COL_5,foto);


        String sql = "INSERT INTO "+TABLE_NAME+" (achternaam,voornaam,saldo) VALUES(?,?,?)";
        SQLiteStatement insertStmt = db.compileStatement(sql);
        insertStmt.clearBindings();
        insertStmt.bindString(1, achternaam);
        insertStmt.bindString(2,voornaam);
        insertStmt.bindString(3, saldo);
        insertStmt.executeInsert();


        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllDataCursor() {
        SQLiteDatabase db = this.getWritableDatabase();
        ///db.execSQL("DROP TABLE "+TABLE_NAME);
        //onCreate(db);
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public ArrayList<Jezelf> getAllDAta(){
        ArrayList<Jezelf> arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);

        while(res.moveToNext()){
            String achternaam=res.getString(0);
            String voornaam=res.getString(1);
            String saldo=res.getString(2);


            Jezelf jezelf=new Jezelf(achternaam,voornaam,saldo);

            arrayList.add(jezelf);
        }
        return arrayList;
    }

    public Cursor KrijgEigenGegevens(String Voornaam){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_NAME+" where VOORNAAM=?", new String [] {Voornaam});
        c.moveToFirst();

        return c;
    }

    public boolean updateData(String achternaam,String voornaam,String saldo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,achternaam);
        contentValues.put(COL_2,voornaam);
        contentValues.put(COL_3,saldo);
        db.update(TABLE_NAME, contentValues, "ACHTERNAAM = ?",new String[] { achternaam });
        return true;
    }

    public Integer deleteData (String achternaam) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ACHTERNAAM = ?",new String[] {achternaam});

    }


}
