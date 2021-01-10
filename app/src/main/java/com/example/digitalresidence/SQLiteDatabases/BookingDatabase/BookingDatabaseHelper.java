package com.example.digitalresidence.SQLiteDatabases.BookingDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BookingDatabaseHelper extends SQLiteOpenHelper {
    public static final String BOOKING_DATABASE_NAME="Booking.db";
    public static final String Booking_TABLE_NAME="Booking_table";
    public static final String ID="_ID";
    public static final String COLUMN_1="BOOKING_OF";
    public static final String COLUMN_2="BOOKING_FOR";
    public static final String COLUMN_3="BOOKING_DESCRIPTION";
    public static final String COLUMN_4="BOOKING_DATE";
    public static final String COLUMN_5="BOOKING_TIME_FROM";
    public static final String COLUMN_6="BOOKING_TIME_TO";
    public static final String COLUMN_7="TIME_STAMP";

    public BookingDatabaseHelper(Context context) {
        super(context, BOOKING_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Booking_TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT,BOOKING_OF TEXT,BOOKING_FOR TEXT,BOOKING_DESCRIPTION TEXT," +
                "BOOKING_DATE TEXT,BOOKING_TIME_FROM TEXT,BOOKING_TIME_TO TEXT,TIME_STAMP DATETIME DEFAULT CURRENT_TIMESTAMP)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Booking_TABLE_NAME );
        onCreate(db);
    }
    public boolean insertBooking(String bookingOf,
                                 String bookingFor,
                                 String description,
                                 String selectDate,
                                 String selectTimeFrom,
                                 String selectTimeTo) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_1,bookingOf);
        contentValues.put(COLUMN_2,bookingFor);
        contentValues.put(COLUMN_3,description);
        contentValues.put(COLUMN_4,selectDate);
        contentValues.put(COLUMN_5,selectTimeFrom);
        contentValues.put(COLUMN_6,selectTimeTo);
        long result=db.insert(Booking_TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
    public List<BookingModel> getAllBooking(){
        List<BookingModel> getBooking= new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Booking_TABLE_NAME ;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                BookingModel bookingModel = new BookingModel();
                bookingModel.setBookingId(cursor.getInt(cursor.getColumnIndex(ID)));
                bookingModel.setBookingOf(cursor.getString(cursor.getColumnIndex(COLUMN_1)));
                bookingModel.setBookingFor(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
                bookingModel.setBookingDescription(cursor.getString(cursor.getColumnIndex(COLUMN_3)));
                bookingModel.setBookingDate(cursor.getString(cursor.getColumnIndex(COLUMN_4)));
                bookingModel.setBookingTimeFrom(cursor.getString(cursor.getColumnIndex(COLUMN_5)));
                bookingModel.setBookingTimeTo(cursor.getString(cursor.getColumnIndex(COLUMN_6)));
                bookingModel.setBookingTimeStamp(cursor.getString(cursor.getColumnIndex(COLUMN_7)));
                getBooking.add(bookingModel);
            }while (cursor.moveToNext());
        }
        db.close();
        return getBooking;
    }
    //delete Complains
    public void deleteBooking(BookingModel bookingModel){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Booking_TABLE_NAME,ID + " =?",
                new String[]{String.valueOf(bookingModel.getBookingId())});
    }
    //Getting Events Count
    public int getBookingsCount(){
        String countQuery = "SELECT * FROM " + Booking_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        //return count
        return count;
    }

}
