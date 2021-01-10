package com.example.digitalresidence.SQLiteDatabases.NoticeDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NoticeDatabaseHelper extends SQLiteOpenHelper {
    public static final String NOTICE_DATABASE_NAME="Notice.db";
    public static final String NOTICE_TABLE_NAME="Notice_table";
    public static final String ID="_ID";
    public static final String COLUMN_1="NOTICE_TITLE";
    public static final String COLUMN_2="NOTICE_APPLICABLE_TO";
    public static final String COLUMN_3="NOTICE_DESCRIPTION";
    public static final String COLUMN_4="NOTICE_TIME_STAMP";


    public NoticeDatabaseHelper(Context context) {
        super(context, NOTICE_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NOTICE_TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT,NOTICE_TITLE TEXT,NOTICE_APPLICABLE_TO TEXT,NOTICE_DESCRIPTION TEXT," +
                "NOTICE_TIME_STAMP DATETIME DEFAULT CURRENT_TIMESTAMP)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+NOTICE_TABLE_NAME );
        onCreate(db);
    }
    //create notice
    public boolean insertNotice(String name,
                                  String applicableTo,
                                  String description) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_1,name);
        contentValues.put(COLUMN_2,applicableTo);
        contentValues.put(COLUMN_3,description);
        long result=db.insert(NOTICE_TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
    public List<NoticeModel> getAllNotice(){
        List<NoticeModel> getNotice= new ArrayList<>();
        String selectQuery = "SELECT * FROM " + NOTICE_TABLE_NAME ;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                NoticeModel noticeModel = new NoticeModel();
                noticeModel.setNoticeId(cursor.getInt(cursor.getColumnIndex(ID)));
                noticeModel.setNoticeTitle(cursor.getString(cursor.getColumnIndex(COLUMN_1)));
                noticeModel.setNoticeApplicableTo(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
                noticeModel.setNoticeDescription(cursor.getString(cursor.getColumnIndex(COLUMN_3)));
                noticeModel.setNoticeTimeStamp(cursor.getString(cursor.getColumnIndex(COLUMN_4)));
                getNotice.add(noticeModel);
            }while (cursor.moveToNext());
        }
        db.close();
        return getNotice;
    }
    //delete Complains
    public void deleteNotice(NoticeModel noticeModel){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTICE_TABLE_NAME,ID + " =?",
                new String[]{String.valueOf(noticeModel.getNoticeId())});
    }
    //Getting Events Count
    public int getNoticesCount(){
        String countQuery = "SELECT * FROM " + NOTICE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        //return count
        return count;
    }
}
