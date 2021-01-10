package com.example.digitalresidence.SQLiteDatabases.VendorDatabase;

public class VendorModel {
    private int VendorId;
    private String VendorCategory;
    private String VendorName;
    private String VendorTime;
    private String VendorStatus;
    private String VendorTimeStamp;

    public VendorModel(){}

    public VendorModel(int vendorId, String vendorCategory, String vendorName, String vendorTime, String vendorStatus, String vendorTimeStamp) {
        VendorId = vendorId;
        VendorCategory = vendorCategory;
        VendorName = vendorName;
        VendorTime = vendorTime;
        VendorStatus = vendorStatus;
        VendorTimeStamp = vendorTimeStamp;
    }

    public int getVendorId() {
        return VendorId;
    }

    public void setVendorId(int vendorId) {
        VendorId = vendorId;
    }

    public String getVendorCategory() {
        return VendorCategory;
    }

    public void setVendorCategory(String vendorCategory) {
        VendorCategory = vendorCategory;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getVendorTime() {
        return VendorTime;
    }

    public void setVendorTime(String vendorTime) {
        VendorTime = vendorTime;
    }

    public String getVendorStatus() {
        return VendorStatus;
    }

    public void setVendorStatus(String vendorStatus) {
        VendorStatus = vendorStatus;
    }

    public String getVendorTimeStamp() {
        return VendorTimeStamp;
    }

    public void setVendorTimeStamp(String vendorTimeStamp) {
        VendorTimeStamp = vendorTimeStamp;
    }
}
