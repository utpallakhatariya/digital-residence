package com.example.digitalresidence.SQLiteDatabases.VendorDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VendorDatabaseHelper extends SQLiteOpenHelper {
    public static final String VENDOR_DATABASE_NAME="Vendor.db";
    public static final String VENDOR_TABLE_NAME="Vendor_table";
    public static final String VENDOR_ID="_ID";
    public static final String COLUMN_1="VENDOR_CATEGORY";
    public static final String COLUMN_2="VENDOR_NAME";
    public static final String COLUMN_3="VENDOR_TIME";
    public static final String COLUMN_4="VENDOR_TIME_STAMP";

    public VendorDatabaseHelper(Context context) {
        super(context, VENDOR_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + VENDOR_TABLE_NAME + "(_ID INTEGER PRIMARY KEY AUTOINCREMENT,VENDOR_CATEGORY TEXT,VENDOR_NAME TEXT,VENDOR_TIME TEXT,VENDOR_TIME_STAMP DATETIME DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + VENDOR_TABLE_NAME);
        onCreate(db);
    }

    //create vendor
    public boolean insertVendor(String category,String name,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1,category);
        contentValues.put(COLUMN_2,name);
        contentValues.put(COLUMN_3,time);
        long result=db.insert(VENDOR_TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
    //getVendor
    public List<VendorModel> getAllVendor(){
        List<VendorModel> getVendor = new ArrayList<>();
        String selectQuery = "select * from " + VENDOR_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                VendorModel vendorModel = new VendorModel();
                vendorModel.setVendorId(cursor.getInt(cursor.getColumnIndex(VENDOR_ID)));
                vendorModel.setVendorCategory(cursor.getString(cursor.getColumnIndex(COLUMN_1)));
                vendorModel.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
                vendorModel.setVendorTime(cursor.getString(cursor.getColumnIndex(COLUMN_3)));
                vendorModel.setVendorTimeStamp(cursor.getString(cursor.getColumnIndex(COLUMN_4)));
                getVendor.add(vendorModel);
            }while (cursor.moveToNext());
        }
        db.close();
        return getVendor;
    }
    //delete Vendor
    public void deleteVendor(VendorModel vendorModel){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VENDOR_TABLE_NAME,VENDOR_ID+ " =?",
                new String[]{String.valueOf(vendorModel.getVendorId())});
    }
    //Getting Events Count
    public int getVendorsCount(){
        String countQuery = "SELECT * FROM " + VENDOR_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        //return count
        return count;
    }
}
