package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 01-Mar-18.
 * FIL AHM
 */

public class UserListPojo implements Serializable {

    @Expose
    @SerializedName("data")
    private List<Data> data;
    @Expose
    @SerializedName("info")
    private Object info;
    @Expose
    @SerializedName("status")
    private boolean status;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Data {

        @Expose
        @SerializedName("dingmode")
        private String dingmode;
        @Expose
        @SerializedName("cashBenificaryCount")
        private int cashBenificaryCount;
        @Expose
        @SerializedName("bankBenificaryCount")
        private int bankBenificaryCount;
        @Expose
        @SerializedName("iPhoneAppUrl")
        private String iPhoneAppUrl;
        @Expose
        @SerializedName("androidAppUrl")
        private String androidAppUrl;
        @Expose
        @SerializedName("userPushNotification")
        private String userPushNotification;
        @Expose
        @SerializedName("userSMSNotification")
        private String userSMSNotification;
        @Expose
        @SerializedName("userEmailNotification")
        private String userEmailNotification;
        @Expose
        @SerializedName("countryISO2Code")
        private String countryISO2Code;
        @Expose
        @SerializedName("countryCurrencySymbol")
        private String countryCurrencySymbol;
        @Expose
        @SerializedName("countryShortCode")
        private String countryShortCode;
        @Expose
        @SerializedName("userCustomerNo")
        private int userCustomerNo;
        @Expose
        @SerializedName("userKYCVerified")
        private String userKYCVerified;
        @Expose
        @SerializedName("userDefaultPassChanged")
        private String userDefaultPassChanged;
        @Expose
        @SerializedName("userStatus")
        private String userStatus;
        @Expose
        @SerializedName("userGPlusID")
        private String userGPlusID;
        @Expose
        @SerializedName("userFBID")
        private String userFBID;
        @Expose
        @SerializedName("userSignupWith")
        private String userSignupWith;
        @Expose
        @SerializedName("userVerified")
        private String userVerified;
        @Expose
        @SerializedName("userLanguageID")
        private String userLanguageID;
        @Expose
        @SerializedName("userReferKey")
        private String userReferKey;
        @Expose
        @SerializedName("userType")
        private String userType;
        @Expose
        @SerializedName("userDeviceID")
        private String userDeviceID;
        @Expose
        @SerializedName("userDeviceType")
        private String userDeviceType;
        @Expose
        @SerializedName("userWalletKey")
        private String userWalletKey;
        @Expose
        @SerializedName("userEmiratesID")
        private String userEmiratesID;
        @Expose
        @SerializedName("userPassportNo")
        private String userPassportNo;
        @Expose
        @SerializedName("userNationality")
        private String userNationality;
        @Expose
        @SerializedName("userProfilePicture")
        private String userProfilePicture;
        @Expose
        @SerializedName("userPin")
        private String userPin;
        @Expose
        @SerializedName("userPassword")
        private String userPassword;
        @Expose
        @SerializedName("userMobile")
        private String userMobile;
        @Expose
        @SerializedName("userCountryCode")
        private String userCountryCode;
        @Expose
        @SerializedName("userEmail")
        private String userEmail;
        @Expose
        @SerializedName("userLongitutde")
        private String userLongitutde;
        @Expose
        @SerializedName("userLattitude")
        private String userLattitude;
        @Expose
        @SerializedName("countryID")
        private int countryID;
        @Expose
        @SerializedName("stateID")
        private int stateID;
        @Expose
        @SerializedName("cityID")
        private int cityID;
        @Expose
        @SerializedName("userAddress")
        private String userAddress;
        @Expose
        @SerializedName("userGender")
        private String userGender;
        @Expose
        @SerializedName("userLastName")
        private String userLastName;
        @Expose
        @SerializedName("userFirstName")
        private String userFirstName;
        @Expose
        @SerializedName("userSecurityAnswer")
        private String userSecurityAnswer;
        @Expose
        @SerializedName("secID")
        private String secID;
        @Expose
        @SerializedName("userID")
        private int userID;

        public String getDingmode() {
            return dingmode;
        }

        public void setDingmode(String dingmode) {
            this.dingmode = dingmode;
        }

        public int getCashBenificaryCount() {
            return cashBenificaryCount;
        }

        public void setCashBenificaryCount(int cashBenificaryCount) {
            this.cashBenificaryCount = cashBenificaryCount;
        }

        public int getBankBenificaryCount() {
            return bankBenificaryCount;
        }

        public void setBankBenificaryCount(int bankBenificaryCount) {
            this.bankBenificaryCount = bankBenificaryCount;
        }

        public String getIPhoneAppUrl() {
            return iPhoneAppUrl;
        }

        public void setIPhoneAppUrl(String iPhoneAppUrl) {
            this.iPhoneAppUrl = iPhoneAppUrl;
        }

        public String getAndroidAppUrl() {
            return androidAppUrl;
        }

        public void setAndroidAppUrl(String androidAppUrl) {
            this.androidAppUrl = androidAppUrl;
        }

        public String getUserPushNotification() {
            return userPushNotification;
        }

        public void setUserPushNotification(String userPushNotification) {
            this.userPushNotification = userPushNotification;
        }

        public String getUserSMSNotification() {
            return userSMSNotification;
        }

        public void setUserSMSNotification(String userSMSNotification) {
            this.userSMSNotification = userSMSNotification;
        }

        public String getUserEmailNotification() {
            return userEmailNotification;
        }

        public void setUserEmailNotification(String userEmailNotification) {
            this.userEmailNotification = userEmailNotification;
        }

        public String getCountryISO2Code() {
            return countryISO2Code;
        }

        public void setCountryISO2Code(String countryISO2Code) {
            this.countryISO2Code = countryISO2Code;
        }

        public String getCountryCurrencySymbol() {
            return countryCurrencySymbol;
        }

        public void setCountryCurrencySymbol(String countryCurrencySymbol) {
            this.countryCurrencySymbol = countryCurrencySymbol;
        }

        public String getCountryShortCode() {
            return countryShortCode;
        }

        public void setCountryShortCode(String countryShortCode) {
            this.countryShortCode = countryShortCode;
        }

        public int getUserCustomerNo() {
            return userCustomerNo;
        }

        public void setUserCustomerNo(int userCustomerNo) {
            this.userCustomerNo = userCustomerNo;
        }

        public String getUserKYCVerified() {
            return userKYCVerified;
        }

        public void setUserKYCVerified(String userKYCVerified) {
            this.userKYCVerified = userKYCVerified;
        }

        public String getUserDefaultPassChanged() {
            return userDefaultPassChanged;
        }

        public void setUserDefaultPassChanged(String userDefaultPassChanged) {
            this.userDefaultPassChanged = userDefaultPassChanged;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getUserGPlusID() {
            return userGPlusID;
        }

        public void setUserGPlusID(String userGPlusID) {
            this.userGPlusID = userGPlusID;
        }

        public String getUserFBID() {
            return userFBID;
        }

        public void setUserFBID(String userFBID) {
            this.userFBID = userFBID;
        }

        public String getUserSignupWith() {
            return userSignupWith;
        }

        public void setUserSignupWith(String userSignupWith) {
            this.userSignupWith = userSignupWith;
        }

        public String getUserVerified() {
            return userVerified;
        }

        public void setUserVerified(String userVerified) {
            this.userVerified = userVerified;
        }

        public String getUserLanguageID() {
            return userLanguageID;
        }

        public void setUserLanguageID(String userLanguageID) {
            this.userLanguageID = userLanguageID;
        }

        public String getUserReferKey() {
            return userReferKey;
        }

        public void setUserReferKey(String userReferKey) {
            this.userReferKey = userReferKey;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getUserDeviceID() {
            return userDeviceID;
        }

        public void setUserDeviceID(String userDeviceID) {
            this.userDeviceID = userDeviceID;
        }

        public String getUserDeviceType() {
            return userDeviceType;
        }

        public void setUserDeviceType(String userDeviceType) {
            this.userDeviceType = userDeviceType;
        }

        public String getUserWalletKey() {
            return userWalletKey;
        }

        public void setUserWalletKey(String userWalletKey) {
            this.userWalletKey = userWalletKey;
        }

        public String getUserEmiratesID() {
            return userEmiratesID;
        }

        public void setUserEmiratesID(String userEmiratesID) {
            this.userEmiratesID = userEmiratesID;
        }

        public String getUserPassportNo() {
            return userPassportNo;
        }

        public void setUserPassportNo(String userPassportNo) {
            this.userPassportNo = userPassportNo;
        }

        public String getUserNationality() {
            return userNationality;
        }

        public void setUserNationality(String userNationality) {
            this.userNationality = userNationality;
        }

        public String getUserProfilePicture() {
            return userProfilePicture;
        }

        public void setUserProfilePicture(String userProfilePicture) {
            this.userProfilePicture = userProfilePicture;
        }

        public String getUserPin() {
            return userPin;
        }

        public void setUserPin(String userPin) {
            this.userPin = userPin;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public String getUserCountryCode() {
            return userCountryCode;
        }

        public void setUserCountryCode(String userCountryCode) {
            this.userCountryCode = userCountryCode;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserLongitutde() {
            return userLongitutde;
        }

        public void setUserLongitutde(String userLongitutde) {
            this.userLongitutde = userLongitutde;
        }

        public String getUserLattitude() {
            return userLattitude;
        }

        public void setUserLattitude(String userLattitude) {
            this.userLattitude = userLattitude;
        }

        public int getCountryID() {
            return countryID;
        }

        public void setCountryID(int countryID) {
            this.countryID = countryID;
        }

        public int getStateID() {
            return stateID;
        }

        public void setStateID(int stateID) {
            this.stateID = stateID;
        }

        public int getCityID() {
            return cityID;
        }

        public void setCityID(int cityID) {
            this.cityID = cityID;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getUserGender() {
            return userGender;
        }

        public void setUserGender(String userGender) {
            this.userGender = userGender;
        }

        public String getUserLastName() {
            return userLastName;
        }

        public void setUserLastName(String userLastName) {
            this.userLastName = userLastName;
        }

        public String getUserFirstName() {
            return userFirstName;
        }
        public String getUserSecurityAnswer() {
            return userSecurityAnswer;
        }
        public String getSecID() {
            return secID;
        }

        public void setUserFirstName(String userFirstName) {
            this.userFirstName = userFirstName;
        }
        public void setSecID(String secID) {
            this.secID = secID;
        }
        public void setUserSecurityAnswer(String userSecurityAnswer) {
            this.userSecurityAnswer = userSecurityAnswer;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

    }

}
