package com.example.digitalresidence.SQLiteDatabases.EventDatabase;

public class EventModel {
        private int id;
        private String Name;
        private String Place;
        private String Description;
        private String Date;
        private String TimeFrom;
        private String TimeTo;
        private String TimeStamp;

        public EventModel(){ }

    public EventModel(int id, String name, String place, String description, String date, String timeFrom, String timeTo, String timestamp) {
        this.id = id;
        this.Name = name;
        this.Place = place;
        this.Description = description;
        this.Date = date;
        this.TimeFrom = timeFrom;
        this.TimeTo = timeTo;
        this.TimeStamp = timestamp;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTimeFrom() {
        return TimeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        TimeFrom = timeFrom;
    }

    public String getTimeTo() {
        return TimeTo;
    }

    public void setTimeTo(String timeTo) {
        TimeTo = timeTo;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }
}
