package com.example.digitalresidence.SQLiteDatabases.GuestDatabase;

public class GuestModel {
    public int GuestId;
    public String GuestName;
    public String GuestOwnerAddress;
    public String GuestPurpose;
    public String GuestCount;
    public String GuestTime;
    public String GuestTimeStamp;

    public GuestModel(){}

    public GuestModel(int guestId, String guestName, String guestOwnerAddress, String guestPurpose, String guestCount, String guestTime, String guestTimeStamp) {
        GuestId = guestId;
        GuestName = guestName;
        GuestOwnerAddress = guestOwnerAddress;
        GuestPurpose = guestPurpose;
        GuestCount = guestCount;
        GuestTime = guestTime;
        GuestTimeStamp = guestTimeStamp;
    }

    public int getGuestId() {
        return GuestId;
    }

    public void setGuestId(int guestId) {
        GuestId = guestId;
    }

    public String getGuestName() {
        return GuestName;
    }

    public void setGuestName(String guestName) {
        GuestName = guestName;
    }

    public String getGuestOwnerAddress() {
        return GuestOwnerAddress;
    }

    public void setGuestOwnerAddress(String guestOwnerAddress) {
        GuestOwnerAddress = guestOwnerAddress;
    }

    public String getGuestPurpose() {
        return GuestPurpose;
    }

    public void setGuestPurpose(String guestPurpose) {
        GuestPurpose = guestPurpose;
    }

    public String getGuestCount() {
        return GuestCount;
    }

    public void setGuestCount(String guestCount) {
        GuestCount = guestCount;
    }

    public String getGuestTime() {
        return GuestTime;
    }

    public void setGuestTime(String guestTime) {
        GuestTime = guestTime;
    }

    public String getGuestTimeStamp() {
        return GuestTimeStamp;
    }

    public void setGuestTimeStamp(String guestTimeStamp) {
        GuestTimeStamp = guestTimeStamp;
    }

}
