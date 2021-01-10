package com.example.digitalresidence.SQLiteDatabases.SocietyDatabase;

public class SocietyModel {
    public int SocietyId;
    public String SocietyName;
    public String SocietySecretaryName;
    public String SocietyGender;
    public String SocietyAge;
    public String SocietyMobileNumber;
    public String SocietyEmail;
    public String SocietyFlatNumber;
    public String SocietyCity;
    public String SocietyPinCode;
    public String SocietyState;
    public String SocietyCountry;


    public SocietyModel(){}

    public SocietyModel(int societyId, String societyName, String societySecretaryName, String societyGender, String societyAge, String societyMobileNumber, String societyEmail, String societyFlateNumber, String societyCity, String societyPinCode, String societyState, String societyCountry) {
        SocietyId = societyId;
        SocietyName = societyName;
        SocietySecretaryName = societySecretaryName;
        SocietyGender = societyGender;
        SocietyAge = societyAge;
        SocietyMobileNumber = societyMobileNumber;
        SocietyEmail = societyEmail;
        SocietyFlatNumber = societyFlateNumber;
        SocietyCity = societyCity;
        SocietyPinCode = societyPinCode;
        SocietyState = societyState;
        SocietyCountry = societyCountry;
    }

    public int getSocietyId() {
        return SocietyId;
    }

    public void setSocietyId(int societyId) {
        SocietyId = societyId;
    }

    public String getSocietyName() {
        return SocietyName;
    }

    public void setSocietyName(String societyName) {
        SocietyName = societyName;
    }

    public String getSocietySecretaryName() {
        return SocietySecretaryName;
    }

    public void setSocietySecretaryName(String societySecretaryName) {
        SocietySecretaryName = societySecretaryName;
    }

    public String getSocietyGender() {
        return SocietyGender;
    }

    public void setSocietyGender(String societyGender) {
        SocietyGender = societyGender;
    }

    public String getSocietyAge() {
        return SocietyAge;
    }

    public void setSocietyAge(String societyAge) {
        SocietyAge = societyAge;
    }

    public String getSocietyMobileNumber() {
        return SocietyMobileNumber;
    }

    public void setSocietyMobileNumber(String societyMobileNumber) {
        SocietyMobileNumber = societyMobileNumber;
    }

    public String getSocietyEmail() {
        return SocietyEmail;
    }

    public void setSocietyEmail(String societyEmail) {
        SocietyEmail = societyEmail;
    }

    public String getSocietyFlatNumber() {
        return SocietyFlatNumber;
    }

    public void setSocietyFlatNumber(String societyFlatNumber) {
        SocietyFlatNumber = societyFlatNumber;
    }

    public String getSocietyCity() {
        return SocietyCity;
    }

    public void setSocietyCity(String societyCity) {
        SocietyCity = societyCity;
    }

    public String getSocietyPinCode() {
        return SocietyPinCode;
    }

    public void setSocietyPinCode(String societyPinCode) {
        SocietyPinCode = societyPinCode;
    }

    public String getSocietyState() {
        return SocietyState;
    }

    public void setSocietyState(String societyState) {
        SocietyState = societyState;
    }

    public String getSocietyCountry() {
        return SocietyCountry;
    }

    public void setSocietyCountry(String societyCountry) {
        SocietyCountry = societyCountry;
    }
}
