package com.example.digitalresidence.SQLiteDatabases.ContactDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ContactDatabaseHelper extends SQLiteOpenHelper {
    public static final String CONTACT_DATABASE_NAME="Contact.db";
    public static final String CONTACT_TABLE_NAME="contact_table";
    public static final String CONTACT_ID="contact_id";
    public static final String CONTACT_NAME="contact_name";
    public static final String CONTACT_NUMBER="contact_number";

    public ContactDatabaseHelper(Context context) {
        super(context, CONTACT_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CONTACT_TABLE_NAME + "(contact_id INTEGER PRIMARY KEY AUTOINCREMENT,contact_name TEXT,contact_number TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + CONTACT_TABLE_NAME);
        onCreate(db);
    }
    public boolean insertContact(String name,String number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_NAME,name);
        contentValues.put(CONTACT_NUMBER,number);
        long result = db.insert(CONTACT_TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }

    public boolean updateContact(int id, String name, String number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_ID, id);
        contentValues.put(CONTACT_NAME, name);
        contentValues.put(CONTACT_NUMBER, number);
        long result = db.update(CONTACT_TABLE_NAME,contentValues,CONTACT_ID + "=?",
                new String[]{id+""});
        if (result ==-1)
            return false;
        else
            return true;
    }

    public List<ContactModel> getAllContact(){
        List<ContactModel> getContact = new ArrayList<>();
        String selectQuery = "select * from " + CONTACT_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                ContactModel contactModel = new ContactModel();
                contactModel.setContactId(cursor.getInt(cursor.getColumnIndex(CONTACT_ID)));
                contactModel.setContactName(cursor.getString(cursor.getColumnIndex(CONTACT_NAME)));
                contactModel.setContactNumber(cursor.getString(cursor.getColumnIndex(CONTACT_NUMBER)));
                getContact.add(contactModel);
            }while (cursor.moveToNext());
        }
        db.close();
        return getContact;
    }
    public void deleteContact(ContactModel contactModel){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACT_TABLE_NAME,CONTACT_ID + "=?",
                new String[]{String.valueOf(contactModel.getContactId())});
    }
    public int getVendorsCount(){
        String countQuery = "SELECT * FROM " + CONTACT_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        //return count
        return count;
    }
}
