package com.example.digitalresidence.SQLiteDatabases.GuestDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GuestDatabaseHelper extends SQLiteOpenHelper {
    public static final String GUEST_DATABASE_NAME = "Guest.db";
    public static final String GUEST_TABLE_NAME = "guest_table";
    public static final String GUEST_ID = "GUEST_id";
    public static final String GUEST_COLUMN_1 = "GUEST_NAME";
    public static final String GUEST_COLUMN_2 = "GUEST_ADDRESS";
    public static final String GUEST_COLUMN_3 = "GUEST_PURPOSE";
    public static final String GUEST_COLUMN_4 = "GUEST_PERSON";
    public static final String GUEST_COLUMN_5 = "GUEST_TIME";
    public static final String GUEST_COLUMN_6 = "GUEST_TIME_STAMP";

    public GuestDatabaseHelper(Context context) {
        super(context, GUEST_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + GUEST_TABLE_NAME + "(GUEST_id INTEGER PRIMARY KEY AUTOINCREMENT,GUEST_NAME TEXT," +
                "GUEST_ADDRESS TEXT,GUEST_PURPOSE TEXT,GUEST_PERSON TEXT,GUEST_TIME TEXT,GUEST_TIME_STAMP DATETIME DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("select * from " + GUEST_TABLE_NAME);
        onCreate(db);
    }
    public boolean insertGuest(String guestName,String guestAddress,String guestPurpose,String guestPerson,String guestTIme){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GUEST_COLUMN_1,guestName);
        contentValues.put(GUEST_COLUMN_2,guestAddress);
        contentValues.put(GUEST_COLUMN_3,guestPurpose);
        contentValues.put(GUEST_COLUMN_4,guestPerson);
        contentValues.put(GUEST_COLUMN_5,guestTIme);
        long result = db.insert(GUEST_TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
    public List<GuestModel> getAllGuest(){
        List<GuestModel> getGuest = new ArrayList<>();
        String selectQuery = "select * from " + GUEST_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                GuestModel guestModel = new GuestModel();
                guestModel.setGuestId(cursor.getInt(cursor.getColumnIndex(GUEST_ID)));
                guestModel.setGuestName(cursor.getString(cursor.getColumnIndex(GUEST_COLUMN_1)));
                guestModel.setGuestOwnerAddress(cursor.getString(cursor.getColumnIndex(GUEST_COLUMN_2)));
                guestModel.setGuestPurpose(cursor.getString(cursor.getColumnIndex(GUEST_COLUMN_3)));
                guestModel.setGuestCount(cursor.getString(cursor.getColumnIndex(GUEST_COLUMN_4)));
                guestModel.setGuestTime(cursor.getString(cursor.getColumnIndex(GUEST_COLUMN_5)));
                guestModel.setGuestTimeStamp(cursor.getString(cursor.getColumnIndex(GUEST_COLUMN_6)));
                getGuest.add(guestModel);
            }while (cursor.moveToNext());
        }
        db.close();
        return getGuest;
    }

    public void deleteGuest(GuestModel guestModel){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GUEST_TABLE_NAME,GUEST_ID + " =?",
                new String[]{String.valueOf(guestModel.getGuestId())});
    }

    //Getting Events Count
    public int getGuestsCount(){
        String countQuery = "SELECT * FROM " + GUEST_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        //return count
        return count;
    }
}
