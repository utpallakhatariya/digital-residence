package com.example.digitalresidence.SQLiteDatabases.VisitorDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VisitorDatabaseHelper extends SQLiteOpenHelper {
    private static final String VISITOR_DATABASE_NAME = "Visitor.db";
    private static final String VISITOR_TABLE_NAME = "visitor_table";
    private static final String VISITOR_ID = "VISITOR_id";
    private static final String COLUMN_1 = "VISITOR_NAME";
    private static final String COLUMN_2 = "VISITOR_ADDRESS";
    private static final String COLUMN_3 = "VISITOR_PURPOSE";
    private static final String COLUMN_4 = "VISITOR_TIME";
    private static final String COLUMN_5 = "VISITOR_TIME_STAMP";

    public VisitorDatabaseHelper(Context context) {
        super(context, VISITOR_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + VISITOR_TABLE_NAME + "(VISITOR_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "VISITOR_NAME TEXT,VISITOR_ADDRESS TEXT,VISITOR_PURPOSE TEXT,VISITOR_TIME TEXT," +
                "VISITOR_TIME_STAMP DATETIME DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + VISITOR_TABLE_NAME);
        onCreate(db);
    }
    public boolean insertVisitor(String name,String address,String purpose,String time){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1,name);
        contentValues.put(COLUMN_2,address);
        contentValues.put(COLUMN_3,purpose);
        contentValues.put(COLUMN_4,time);
        long result = database.insert(VISITOR_TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
    public List<VisitorModel> getAllVisitor(){
        List<VisitorModel> getVisitor = new ArrayList<>();
        String selectQuery = "select * from " + VISITOR_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                VisitorModel visitorModel = new VisitorModel();
                visitorModel.setVisitorId(cursor.getInt(cursor.getColumnIndex(VISITOR_ID)));
                visitorModel.setVisitorName(cursor.getString(cursor.getColumnIndex(COLUMN_1)));
                visitorModel.setVisitorAddress(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
                visitorModel.setVisitorPurpose(cursor.getString(cursor.getColumnIndex(COLUMN_3)));
                visitorModel.setVisitorTime(cursor.getString(cursor.getColumnIndex(COLUMN_4)));
                visitorModel.setVisitorTimeStamp(cursor.getString(cursor.getColumnIndex(COLUMN_5)));
                getVisitor.add(visitorModel);
            }while (cursor.moveToNext());
        }
        database.close();
        return getVisitor;
    }

    public void deleteVisitor(VisitorModel visitorModel){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(VISITOR_TABLE_NAME,VISITOR_ID +"=?",
                new String[]{String.valueOf(visitorModel.getVisitorId())});
    }

    //Getting Events Count
    public int getVisitorsCount(){
        String countQuery = "SELECT * FROM " + VISITOR_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        //return count
        return count;
    }
}
