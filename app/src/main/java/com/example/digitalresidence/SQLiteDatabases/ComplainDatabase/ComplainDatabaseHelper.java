package com.example.digitalresidence.SQLiteDatabases.ComplainDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class ComplainDatabaseHelper extends SQLiteOpenHelper {
    public static final String COMPLAINT_DATABASE_NAME="Complaint.db";
    public static final String COMPLAINT_TABLE_NAME="Complaint_table";
    public static final String ID="_ID";
    public static final String COLUMN_1="COMPLAIN_TITLE";
    public static final String COLUMN_2="COMPLAIN_DESCRIPTION";
    public static final String COLUMN_3="TIME_STAMP";

    public ComplainDatabaseHelper(Context context) {
        super(context, COMPLAINT_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + COMPLAINT_TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT,COMPLAIN_TITLE TEXT," +
                "COMPLAIN_DESCRIPTION TEXT," +
                "TIME_STAMP DATETIME DEFAULT CURRENT_TIMESTAMP)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+COMPLAINT_TABLE_NAME );
        onCreate(db);
    }
    public boolean insertComplain(String name,
                              String description) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_1,name);
        contentValues.put(COLUMN_2,description);
        long result=db.insert(COMPLAINT_TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
    public List<ComplaintModel> getAllComplain(){
        List<ComplaintModel> getComplain= new ArrayList<>();
        String selectQuery = "SELECT * FROM " + COMPLAINT_TABLE_NAME ;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                ComplaintModel complaintModel = new ComplaintModel();
                complaintModel.setComplaintid(cursor.getInt(cursor.getColumnIndex(ID)));
                complaintModel.setComplaintTitle(cursor.getString(cursor.getColumnIndex(COLUMN_1)));
                complaintModel.setComplaintDescription(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
                complaintModel.setComplaintTimeStamp(cursor.getString(cursor.getColumnIndex(COLUMN_3)));
                getComplain.add(complaintModel);
            }while (cursor.moveToNext());
        }
        db.close();
        return getComplain;
    }

    //delete Complains
    public void deleteComplaint(ComplaintModel complaintModel){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COMPLAINT_TABLE_NAME,ID + " =?",
                new String[]{String.valueOf(complaintModel.getComplaintid())});
    }
    //Getting Events Count
    public int getComplainsCount(){
        String countQuery = "SELECT * FROM " + COMPLAINT_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        //return count
        return count;
    }
}
