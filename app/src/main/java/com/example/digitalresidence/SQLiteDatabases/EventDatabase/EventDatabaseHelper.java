package com.example.digitalresidence.SQLiteDatabases.EventDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class EventDatabaseHelper extends SQLiteOpenHelper {
    public static final String EVENT_DATABASE_NAME="Event.db";
    public static final String EVENT_TABLE_NAME="event_table";
    public static final String ID="_ID";
    public static final String COLUMN_1="EVENT_NAME";
    public static final String COLUMN_2="EVENT_PLACE";
    public static final String COLUMN_3="EVENT_DESCRIPTION";
    public static final String COLUMN_4="EVENT_DATE";
    public static final String COLUMN_5="EVENT_TIME_FROM";
    public static final String COLUMN_6="EVENT_TIME_TO";
    public static final String COLUMN_7="TIME_STAMP";

    public EventDatabaseHelper(Context context){
        super(context, EVENT_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + EVENT_TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT,EVENT_NAME TEXT,EVENT_PLACE TEXT,EVENT_DESCRIPTION TEXT," +
                "EVENT_DATE TEXT,EVENT_TIME_FROM TEXT,EVENT_TIME_TO TEXT,TIME_STAMP DATETIME DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+EVENT_TABLE_NAME );
        onCreate(db);
    }

    public boolean insertData(String name,
                              String place,
                              String description,
                              String date,
                              String timeFrom,
                              String timeTo) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_1,name);
        contentValues.put(COLUMN_2,place);
        contentValues.put(COLUMN_3,description);
        contentValues.put(COLUMN_4,date);
        contentValues.put(COLUMN_5,timeFrom);
        contentValues.put(COLUMN_6,timeTo);
        long result=db.insert(EVENT_TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }

   /* //get single event
    EventModel getEvent(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EVENT_TABLE_NAME,new String[]
                {ID,COLUMN_1,COLUMN_2,COLUMN_3,COLUMN_4,COLUMN_5,COLUMN_6},
                ID + "=?",new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        EventModel eventModel = new EventModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2),cursor.getString(3),
                cursor.getString(4),cursor.getString(5),cursor.getString(6));

        //return event
        return eventModel;
    }
*/
    //get all events in list
    public List<EventModel> getAllData(){
        List<EventModel> getEvent= new ArrayList<>();
        String selectQuery = "SELECT * FROM " + EVENT_TABLE_NAME ;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                EventModel eventModel = new EventModel();
                eventModel.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                eventModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_1)));
                eventModel.setPlace(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
                eventModel.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_3)));
                eventModel.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_4)));
                eventModel.setTimeFrom(cursor.getString(cursor.getColumnIndex(COLUMN_5)));
                eventModel.setTimeTo(cursor.getString(cursor.getColumnIndex(COLUMN_6)));
                eventModel.setTimeStamp(cursor.getString(cursor.getColumnIndex(COLUMN_7)));
                getEvent.add(eventModel);
            }while (cursor.moveToNext());
        }
        db.close();
        return getEvent;
    }

    //update Event
    public boolean updateEvent(Integer id,String name,String place,String description,String date,String timeFrom,String timeTo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EVENT_NAME",name);
        contentValues.put("EVENT_PLACE",place);
        contentValues.put("EVENT_DESCRIPTION",description);
        contentValues.put("EVENT_DATE",date);
        contentValues.put("EVENT_TIME_FROM",timeFrom);
        contentValues.put("EVENT_TIME_TO",timeTo);
        db.update("event_table",contentValues,"_ID =? ",new String[]{Integer.toString(id)});
        return true;
    }

    //delete Events
    public void deleteEvent(EventModel eventModel){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EVENT_TABLE_NAME,ID + " =?",
                new String[]{String.valueOf(eventModel.getId())});
    }

    //Getting Events Count
    public int getEventsCount(){
        String countQuery = "SELECT * FROM " + EVENT_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        //return count
        return count;
    }
}
