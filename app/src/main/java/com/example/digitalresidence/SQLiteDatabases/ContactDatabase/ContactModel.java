package com.example.digitalresidence.SQLiteDatabases.ContactDatabase;

public class ContactModel {
    public int ContactId;
    public String ContactName;
    public String  ContactNumber;

    public ContactModel(){}

    public ContactModel(int contactId, String contactName, String contactNumber) {
        ContactId = contactId;
        ContactName = contactName;
        ContactNumber = contactNumber;
    }

    public int getContactId() {
        return ContactId;
    }

    public void setContactId(int contactId) {
        ContactId = contactId;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }
}
