package com.example.digitalresidence.SQLiteDatabases.MeetingDatabase;

public class MeetingModel {
    private int MeetingId;
    private String MeetingTitle;
    private String MeetingPlace;
    private String MeetingDescription;
    private String MeetingDate;
    private String MeetingTime;
    private String MeetingTimeStamp;

    public MeetingModel(){}

    public MeetingModel(int meetingId, String meetingTitle, String meetingPlace, String meetingDescription,String meetingDate,String meetingTime, String meetingTimeStamp) {
        MeetingId = meetingId;
        MeetingTitle = meetingTitle;
        MeetingPlace = meetingPlace;
        MeetingDescription = meetingDescription;
        MeetingDate = meetingDate;
        MeetingTime = meetingTime;
        MeetingTimeStamp = meetingTimeStamp;
    }

    public int getMeetingId() {
        return MeetingId;
    }

    public void setMeetingId(int meetingId) {
        MeetingId = meetingId;
    }

    public String getMeetingTitle() {
        return MeetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        MeetingTitle = meetingTitle;
    }

    public String getMeetingPlace() {
        return MeetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        MeetingPlace = meetingPlace;
    }

    public String getMeetingDescription() {
        return MeetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        MeetingDescription = meetingDescription;
    }

    public String getMeetingDate() {
        return MeetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        MeetingDate = meetingDate;
    }

    public String getMeetingTime() {
        return MeetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        MeetingTime = meetingTime;
    }

    public String getMeetingTimeStamp() {
        return MeetingTimeStamp;
    }

    public void setMeetingTimeStamp(String meetingTimeStamp) {
        MeetingTimeStamp = meetingTimeStamp;
    }
}
