package com.example.digitalresidence.SQLiteDatabases.VisitorDatabase;

public class VisitorModel {
    private int VisitorId;
    private String VisitorName;
    private String VisitorAddress;
    private String VisitorPurpose;
    private String VisitorTime;
    private String VisitorTimeStamp;

    public VisitorModel(){}

    public VisitorModel(int visitorId, String visitorName, String visitorAddress, String visitorPurpose, String visitorTime, String visitorTimeStamp) {
        VisitorId = visitorId;
        VisitorName = visitorName;
        VisitorAddress = visitorAddress;
        VisitorPurpose = visitorPurpose;
        VisitorTime = visitorTime;
        VisitorTimeStamp = visitorTimeStamp;
    }

    public int getVisitorId() {
        return VisitorId;
    }

    public void setVisitorId(int visitorId) {
        VisitorId = visitorId;
    }

    public String getVisitorName() {
        return VisitorName;
    }

    public void setVisitorName(String visitorName) {
        VisitorName = visitorName;
    }

    public String getVisitorAddress() {
        return VisitorAddress;
    }

    public void setVisitorAddress(String visitorAddress) {
        VisitorAddress = visitorAddress;
    }

    public String getVisitorPurpose() {
        return VisitorPurpose;
    }

    public void setVisitorPurpose(String visitorPurpose) {
        VisitorPurpose = visitorPurpose;
    }

    public String getVisitorTime() {
        return VisitorTime;
    }

    public void setVisitorTime(String visitorTime) {
        VisitorTime = visitorTime;
    }

    public String getVisitorTimeStamp() {
        return VisitorTimeStamp;
    }

    public void setVisitorTimeStamp(String visitorTimeStamp) {
        VisitorTimeStamp = visitorTimeStamp;
    }
}
