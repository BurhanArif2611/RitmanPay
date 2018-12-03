package com.fil.workerappz.pojo;

import java.io.Serializable;

/**
 * Created by HS on 19-Mar-18.
 * FIL AHM
 */

public class BeneficiaryInfoListPojo implements Serializable {
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLandMark() {
        return LandMark;
    }

    public void setLandMark(String landMark) {
        LandMark = landMark;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getIDType() {
        return IDType;
    }

    public void setIDType(String IDType) {
        this.IDType = IDType;
    }

    private String FirstName="";
    private String MiddleName="";
    private String LastName="";
    private String NickName="";
    private String Address="";
    private String LandMark="";
    private String ZipCode="";
    private String EmailID="";
    private String DateOfBirth="";

    public String getIdExpireyDate() {
        return IdExpireyDate;
    }

    public void setIdExpireyDate(String idExpireyDate) {
        IdExpireyDate = idExpireyDate;
    }

    private String IdExpireyDate="";

    public String getIdIssueDate() {
        return IdIssueDate;
    }

    public void setIdIssueDate(String idIssueDate) {
        IdIssueDate = idIssueDate;
    }

    private String IdIssueDate="";
    private String Telephone="";
    private String Nationality="";
    private String IDNumber="";
    private String IDType="";

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    private String relation="";

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    private String State="";

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    private String City="";

    public String getCountryFlagImage() {
        return CountryFlagImage;
    }

    public void setCountryFlagImage(String countryFlagImage) {
        CountryFlagImage = countryFlagImage;
    }

    private String CountryFlagImage="";

    public String getIDtype_Description() {
        return IDtype_Description;
    }

    public void setIDtype_Description(String IDtype_Description) {
        this.IDtype_Description = IDtype_Description;
    }

    private String IDtype_Description="";

    public String getBeneficiarynumber() {
        return beneficiarynumber;
    }

    public void setBeneficiarynumber(String beneficiarynumber) {
        this.beneficiarynumber = beneficiarynumber;
    }

    private String beneficiarynumber="";

    public String getPayoutbranchcode() {
        return payoutbranchcode;
    }

    public void setPayoutbranchcode(String payoutbranchcode) {
        this.payoutbranchcode = payoutbranchcode;
    }

    public String getPayoutcurrency() {
        return payoutcurrency;
    }

    public void setPayoutcurrency(String payoutcurrency) {
        this.payoutcurrency = payoutcurrency;
    }

    private String payoutbranchcode="";
    private String payoutcurrency="";

    public String getPayoutcountry() {
        return payoutcountry;
    }

    public void setPayoutcountry(String payoutcountry) {
        this.payoutcountry = payoutcountry;
    }

    private String payoutcountry="";

    public String getActivitytype() {
        return activitytype;
    }

    public void setActivitytype(String activitytype) {
        this.activitytype = activitytype;
    }

    private String activitytype="";


}