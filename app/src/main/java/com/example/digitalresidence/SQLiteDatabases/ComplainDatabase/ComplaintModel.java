package com.example.digitalresidence.SQLiteDatabases.ComplainDatabase;

public class ComplaintModel {
    private int complaintid;
    private String complaintTitle;
    private String complaintDescription;
    private String complaintTimeStamp;

    public ComplaintModel(){}

    public ComplaintModel(int id, String title, String description, String timeStamp) {
        this.complaintid = id;
        complaintTitle = title;
        complaintDescription = description;
        complaintTimeStamp = timeStamp;
    }

    public int getComplaintid() {
        return complaintid;
    }

    public void setComplaintid(int complaintid) {
        this.complaintid = complaintid;
    }

    public String getComplaintTitle() {
        return complaintTitle;
    }

    public void setComplaintTitle(String complaintTitle) {
        this.complaintTitle = complaintTitle;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public String getComplaintTimeStamp() {
        return complaintTimeStamp;
    }

    public void setComplaintTimeStamp(String complaintTimeStamp) {
        this.complaintTimeStamp = complaintTimeStamp;
    }
}
