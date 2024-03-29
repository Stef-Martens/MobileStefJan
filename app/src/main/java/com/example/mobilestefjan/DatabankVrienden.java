package com.example.mobilestefjan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class DatabankVrienden extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Vrienden.db";
    public static final String TABLE_NAME = "vrienden_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ACHTERNAAM";
    public static final String COL_3 = "VOORNAAM";
    public static final String COL_4 = "GELD";

    public DatabankVrienden(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ACHTERNAAM TEXT,VOORNAAM TEXT,GELD INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(String achternaam,String voornaam,String geld) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,achternaam);
        contentValues.put(COL_3,voornaam);
        contentValues.put(COL_4,geld);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllDataCursor() {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DROP TABLE "+TABLE_NAME);
        //onCreate(db);
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public ArrayList<Vrienden> getAllDAta(){
        ArrayList<Vrienden> arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);

        while(res.moveToNext()){
            String Achternaam=res.getString(1);
            String Voornaam=res.getString(2);
            String Geld=res.getString(3);


            Vrienden vrienden=new Vrienden(Achternaam,Voornaam,Geld);

            arrayList.add(vrienden);
        }
        return arrayList;
    }

    public boolean updateData(Integer id,String achternaam,String voornaam,String geld) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,achternaam);
        contentValues.put(COL_3,voornaam);
        contentValues.put(COL_4,geld);
        db.update(TABLE_NAME, contentValues, "ACHTERNAAM = ? and VOORNAAM=?",new String[] { achternaam, voornaam });
        return true;
    }

    public boolean ifExists(String achternaam, String voornaam) {
        // Check before adding item if item already exist
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_NAME+" where ACHTERNAAM=? and VOORNAAM=?", new String [] {achternaam, voornaam});
        boolean exist=false;
        if((c.getCount() > 0)){
            exist=true;
        }
        c.close();
        return exist;

    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});

    }

    public void MaakOpnieuwDatabankAan(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE "+TABLE_NAME);
        onCreate(db);
    }

    public Cursor KrijgVriend(String Voornaam){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_NAME+" where VOORNAAM=?", new String [] {Voornaam});
        c.moveToFirst();

        return c;
    }

    public List<String> krijgAlleVriendenIndll() {
        List<String> labels = new ArrayList<String>();


        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        return labels;

    }
}