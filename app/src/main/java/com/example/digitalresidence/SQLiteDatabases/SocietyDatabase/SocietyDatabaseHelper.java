package com.example.digitalresidence.SQLiteDatabases.SocietyDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SocietyDatabaseHelper extends SQLiteOpenHelper {
    public static final String SOCIETY_DATABASE_NAME="Society.db";
    public static final String SOCIETY_TABLE_NAME="Society_table";
    public static final String SocietyID="_ID";
    public static final String COLUMN_1="SOCIETY_Name";
    public static final String COLUMN_2="SOCIETY_Full_Name";
/*    public static final String COLUMN_3="SOCIETY_Gender";
    public static final String COLUMN_4="SOCIETY_Age";
    public static final String COLUMN_5="SOCIETY_Mobile_Number";
    public static final String COLUMN_6="SOCIETY_Email";
    public static final String COLUMN_7="SOCIETY_Flat";
    public static final String COLUMN_8="SOCIETY_City";
    public static final String COLUMN_9="SOCIETY_Pin_Code";
    public static final String COLUMN_10="SOCIETY_State";
    public static final String COLUMN_11="SOCIETY_Country";*/


    public SocietyDatabaseHelper(@Nullable Context context) {
        super(context, SOCIETY_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SOCIETY_TABLE_NAME + "(_ID INTEGER PRIMARY KEY AUTOINCREMENT,SOCIETY_Name TEXT,SOCIETY_Full_Name TEXT/*," +
                "SOCIETY_Gender TEXT,SOCIETY_Age TEXT,SOCIETY_Mobile_Number TEXT,SOCIETY_Email TEXT," +
                "SOCIETY_Flat TEXT,SOCIETY_City TEXT,SOCIETY_Pin_Code TEXT,SOCIETY_State TEXT,SOCIETY_Country TEXT*/)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + SOCIETY_TABLE_NAME);
        onCreate(db);
    }
    public boolean insertSociety(String SSocietyName,String SFullName/*,String SGender,
                                 String SAge,String SMobileNumber,
                                 String SEmail,String SFlat,
                                 String SCity,String SPinCode,
                                 String SSate,String SCountry*/){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1,SSocietyName);
        contentValues.put(COLUMN_2,SFullName);
/*        contentValues.put(COLUMN_3,SGender);
        contentValues.put(COLUMN_4,SAge);
        contentValues.put(COLUMN_5,SMobileNumber);
        contentValues.put(COLUMN_6,SEmail);
        contentValues.put(COLUMN_7,SFlat);
        contentValues.put(COLUMN_8,SCity);
        contentValues.put(COLUMN_9,SPinCode);
        contentValues.put(COLUMN_10,SSate);
        contentValues.put(COLUMN_11,SCountry);*/
        long result = db.insert(SOCIETY_TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
    public List<SocietyModel> getAllSociety(){
        List<SocietyModel> getSociety = new ArrayList<>();
        String societyQuery = "select * from " + SOCIETY_TABLE_NAME;
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.rawQuery(societyQuery,null);
        if (cursor.moveToFirst()){
            do {
                SocietyModel societyModel = new SocietyModel();
                societyModel.setSocietyId(cursor.getInt(cursor.getColumnIndex(SocietyID)));
                societyModel.setSocietyName(cursor.getString(cursor.getColumnIndex(COLUMN_1)));
                societyModel.setSocietySecretaryName(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
                /*societyModel.setSocietyGender(cursor.getString(cursor.getColumnIndex(COLUMN_3)));
                societyModel.setSocietyAge(cursor.getString(cursor.getColumnIndex(COLUMN_4)));
                societyModel.setSocietyMobileNumber(cursor.getString(cursor.getColumnIndex(COLUMN_5)));
                societyModel.setSocietyEmail(cursor.getString(cursor.getColumnIndex(COLUMN_6)));
                societyModel.setSocietyFlatNumber(cursor.getString(cursor.getColumnIndex(COLUMN_7)));
                societyModel.setSocietyCity(cursor.getString(cursor.getColumnIndex(COLUMN_8)));
                societyModel.setSocietyPinCode(cursor.getString(cursor.getColumnIndex(COLUMN_9)));
                societyModel.setSocietyState(cursor.getString(cursor.getColumnIndex(COLUMN_10)));
                societyModel.setSocietyCountry(cursor.getString(cursor.getColumnIndex(COLUMN_11)));*/
                getSociety.add(societyModel);
            }while (cursor.moveToNext());
         }
        db.close();
        return getSociety;
    }

}
