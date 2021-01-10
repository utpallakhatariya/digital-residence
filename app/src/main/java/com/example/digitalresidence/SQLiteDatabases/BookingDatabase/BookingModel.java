package com.example.digitalresidence.SQLiteDatabases.BookingDatabase;

public class BookingModel {
    private int BookingId;
    private String BookingTitle;
    private String BookingOf;
    private String BookingFor;
    private String BookingDescription;
    private String BookingDate;
    private String BookingTimeFrom;
    private String BookingTimeTo;
    private String BookingTimeStamp;


    public BookingModel() {}

    public BookingModel(int bookingId, String bookingTitle, String bookingOf, String bookingFor, String bookingDescription, String bookingDate, String timeFrom, String timeTo, String bookingTimeStamp) {
        BookingId = bookingId;
        BookingTitle = bookingTitle;
        BookingOf = bookingOf;
        BookingFor = bookingFor;
        BookingDescription = bookingDescription;
        BookingDate = bookingDate;
        BookingTimeFrom = timeFrom;
        BookingTimeTo = timeTo;
        BookingTimeStamp = bookingTimeStamp;
    }

    public int getBookingId() {
        return BookingId;
    }

    public void setBookingId(int bookingId) {
        BookingId = bookingId;
    }

    public String getBookingTitle() {
        return BookingTitle;
    }

    public void setBookingTitle(String bookingTitle) {
        BookingTitle = bookingTitle;
    }

    public String getBookingOf() {
        return BookingOf;
    }

    public void setBookingOf(String bookingOf) {
        BookingOf = bookingOf;
    }

    public String getBookingFor() {
        return BookingFor;
    }

    public void setBookingFor(String bookingFor) {
        BookingFor = bookingFor;
    }

    public String getBookingDescription() {
        return BookingDescription;
    }

    public void setBookingDescription(String bookingDescription) {
        BookingDescription = bookingDescription;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public String getBookingTimeFrom() {
        return BookingTimeFrom;
    }

    public void setBookingTimeFrom(String bookingtimeFrom) {
        BookingTimeFrom = bookingtimeFrom;
    }

    public String getBookingTimeTo() {
        return BookingTimeTo;
    }

    public void setBookingTimeTo(String bookingtimeTo) {
        BookingTimeTo = bookingtimeTo;
    }

    public String getBookingTimeStamp() {
        return BookingTimeStamp;
    }

    public void setBookingTimeStamp(String bookingTimeStamp) {
        BookingTimeStamp = bookingTimeStamp;
    }
}