package com.example.digitalresidence.SQLiteDatabases.MeetingDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MeetingDatabaseHelper extends SQLiteOpenHelper {
    public static final String MEETING_DATABASE_NAME="Meeting.db";
    public static final String MEETING_TABLE_NAME="Meeting_table";
    public static final String ID="MEETING_ID";
    public static final String COLUMN_1="MEETING_TITLE";
    public static final String COLUMN_2="MEETING_PLACE";
    public static final String COLUMN_3="MEETING_DESCRIPTION";
    public static final String COLUMN_4="MEETING_DATE";
    public static final String COLUMN_5="MEETING_TIME";
    public static final String COLUMN_6="MEETING_TIME_STAMP";

    public MeetingDatabaseHelper(Context context) {
        super(context, MEETING_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MEETING_TABLE_NAME + " (MEETING_ID INTEGER PRIMARY KEY AUTOINCREMENT,MEETING_TITLE TEXT,MEETING_PLACE TEXT,MEETING_DESCRIPTION TEXT," +
                "MEETING_DATE TEXT,MEETING_TIME TEXT,MEETING_TIME_STAMP DATETIME DEFAULT CURRENT_TIMESTAMP)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+MEETING_TABLE_NAME );
        onCreate(db);
    }
    public boolean insertMeeting(String name,
                                  String place,
                                  String description,
                                 String date,
                                 String time) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_1,name);
        contentValues.put(COLUMN_2,place);
        contentValues.put(COLUMN_3,description);
        contentValues.put(COLUMN_4,date);
        contentValues.put(COLUMN_5,time);
        long result=db.insert(MEETING_TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
    public List<MeetingModel> getAllMeeting(){
        List<MeetingModel> getMeeting= new ArrayList<>();
        String selectQuery = "SELECT * FROM " + MEETING_TABLE_NAME ;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                MeetingModel meetingModel = new MeetingModel();
                meetingModel.setMeetingId(cursor.getInt(cursor.getColumnIndex(ID)));
                meetingModel.setMeetingTitle(cursor.getString(cursor.getColumnIndex(COLUMN_1)));
                meetingModel.setMeetingPlace(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
                meetingModel.setMeetingDescription(cursor.getString(cursor.getColumnIndex(COLUMN_3)));
                meetingModel.setMeetingDate(cursor.getString(cursor.getColumnIndex(COLUMN_4)));
                meetingModel.setMeetingTime(cursor.getString(cursor.getColumnIndex(COLUMN_5)));
                meetingModel.setMeetingTimeStamp(cursor.getString(cursor.getColumnIndex(COLUMN_6)));
                getMeeting.add(meetingModel);
            }while (cursor.moveToNext());
        }
        db.close();
        return getMeeting;
    }
    //delete Meetings
    public void deleteMeeting(MeetingModel meetingModel){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEETING_TABLE_NAME,ID + " =?",
                new String[]{String.valueOf(meetingModel.getMeetingId())});
    }
    //Getting Meetings Count
    public int getMeetingsCount(){
        String countQuery = "SELECT * FROM " + MEETING_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        //return count
        return count;
    }
}
