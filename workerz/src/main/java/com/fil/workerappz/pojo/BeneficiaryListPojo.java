package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class BeneficiaryListPojo implements Serializable {


    @Expose
    @SerializedName("data")
    private List<Data> data;
    @Expose
    @SerializedName("info")
    private String info;
    @Expose
    @SerializedName("status")
    private boolean status;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Data implements Serializable {
        @Expose
        @SerializedName("benificaryBeneficiaryNo")
        private String benificaryBeneficiaryNo;
        @Expose
        @SerializedName("benificaryIDtype_Description")
        private String benificaryIDtype_Description;
        @Expose
        @SerializedName("benificaryCustomerRelation")
        private String benificaryCustomerRelation;
        @Expose
        @SerializedName("benificaryBankBranch")
        private String benificaryBankBranch;
        @Expose
        @SerializedName("benificaryCreatedDate")
        private String benificaryCreatedDate;
        @Expose
        @SerializedName("benificaryState")
        private String benificaryState;
        @Expose
        @SerializedName("benificaryCity")
        private String benificaryCity;
        @Expose
        @SerializedName("benificaryPayoutCountryCode")
        private String benificaryPayoutCountryCode;
        @Expose
        @SerializedName("benificaryPurposeCode")
        private String benificaryPurposeCode;
        @Expose
        @SerializedName("benificaryAccountNumber")
        private String benificaryAccountNumber;
        @Expose
        @SerializedName("benificaryBankCode")
        private String benificaryBankCode;
        @Expose
        @SerializedName("benificaryBranchNameAndAddress")
        private String benificaryBranchNameAndAddress;
        @Expose
        @SerializedName("benificaryBankCountry")
        private String benificaryBankCountry;
        @Expose
        @SerializedName("benificaryBankName")
        private String benificaryBankName;
        @Expose
        @SerializedName("benificaryPayOutBranchCode")
        private String benificaryPayOutBranchCode;
        @Expose
        @SerializedName("benificaryPaymentMode")
        private String benificaryPaymentMode;
        @Expose
        @SerializedName("benificaryPayOutCurrency")
        private String benificaryPayOutCurrency;
        @Expose
        @SerializedName("benificaryIDType")
        private String benificaryIDType;
        @Expose
        @SerializedName("benificaryIDNumber")
        private String benificaryIDNumber;
        @Expose
        @SerializedName("benificaryNationality")
        private String benificaryNationality;
        @Expose
        @SerializedName("benificaryDateOfBirth")
        private String benificaryDateOfBirth;
        @Expose
        @SerializedName("benificaryTelephone")
        private String benificaryTelephone;
        @Expose
        @SerializedName("benificaryEmailID")
        private String benificaryEmailID;
        @Expose
        @SerializedName("benificaryZipCode")
        private String benificaryZipCode;
        @Expose
        @SerializedName("benificaryLandMark")
        private String benificaryLandMark;
        @Expose
        @SerializedName("benificaryMiddleName")
        private String benificaryMiddleName;
        @Expose
        @SerializedName("benificaryNickName")
        private String benificaryNickName;
        @Expose
        @SerializedName("benificaryLastName")
        private String benificaryLastName;
        @Expose
        @SerializedName("benificaryAddress")
        private String benificaryAddress;
        @Expose
        @SerializedName("benificaryFirstName")
        private String benificaryFirstName;
        @Expose
        @SerializedName("benificaryCustomerNo")
        private String benificaryCustomerNo;
        @Expose
        @SerializedName("userID")
        private String userID;
        @Expose
        @SerializedName("benificaryID")
        private String benificaryID;
        @SerializedName("benificaryDestiantionAddress")
        @Expose
        private String benificaryDestiantionAddress;
        @SerializedName("benificaryDestinationLandmark")
        @Expose
        private String benificaryDestinationLandmark;
        @SerializedName("benificaryDestinationZipCode")
        @Expose
        private String benificaryDestinationZipCode;


        public String getBenificaryBeneficiaryNo() {
            return benificaryBeneficiaryNo;
        }

        public void setBenificaryBeneficiaryNo(String benificaryBeneficiaryNo) {
            this.benificaryBeneficiaryNo = benificaryBeneficiaryNo;
        }

        public String getBenificaryIDtype_Description() {
            return benificaryIDtype_Description;
        }
        public String getBenificaryDestiantionAddress() {
            return benificaryDestiantionAddress;
        }

        public void setBenificaryDestiantionAddress(String benificaryDestiantionAddress) {
            this.benificaryDestiantionAddress = benificaryDestiantionAddress;
        }

        public String getBenificaryDestinationLandmark() {
            return benificaryDestinationLandmark;
        }

        public void setBenificaryDestinationLandmark(String benificaryDestinationLandmark) {
            this.benificaryDestinationLandmark = benificaryDestinationLandmark;
        }

        public String getBenificaryDestinationZipCode() {
            return benificaryDestinationZipCode;
        }

        public void setBenificaryDestinationZipCode(String benificaryDestinationZipCode) {
            this.benificaryDestinationZipCode = benificaryDestinationZipCode;
        }


        public void setBenificaryIDtype_Description(String benificaryIDtype_Description) {
            this.benificaryIDtype_Description = benificaryIDtype_Description;
        }

        public String getBenificaryCustomerRelation() {
            return benificaryCustomerRelation;
        }

        public void setBenificaryCustomerRelation(String benificaryCustomerRelation) {
            this.benificaryCustomerRelation = benificaryCustomerRelation;
        }

        public String getBenificaryBankBranch() {
            return benificaryBankBranch;
        }

        public void setBenificaryBankBranch(String benificaryBankBranch) {
            this.benificaryBankBranch = benificaryBankBranch;
        }

        public String getBenificaryCreatedDate() {
            return benificaryCreatedDate;
        }


        public void setBenificaryCreatedDate(String benificaryCreatedDate) {
            this.benificaryCreatedDate = benificaryCreatedDate;
        }

        public String getBenificaryPayoutCountryCode() {
            return benificaryPayoutCountryCode;
        }

        public void setBenificaryPayoutCountryCode(String benificaryPayoutCountryCode) {
            this.benificaryPayoutCountryCode = benificaryPayoutCountryCode;
        }

        public String getBenificaryPurposeCode() {
            return benificaryPurposeCode;
        }

        public void setBenificaryPurposeCode(String benificaryPurposeCode) {
            this.benificaryPurposeCode = benificaryPurposeCode;
        }

        public String getBenificaryAccountNumber() {
            return benificaryAccountNumber;
        }

        public void setBenificaryAccountNumber(String benificaryAccountNumber) {
            this.benificaryAccountNumber = benificaryAccountNumber;
        }

        public String getBenificaryBankCode() {
            return benificaryBankCode;
        }

        public void setBenificaryBankCode(String benificaryBankCode) {
            this.benificaryBankCode = benificaryBankCode;
        }

        public String getBenificaryBranchNameAndAddress() {
            return benificaryBranchNameAndAddress;
        }

        public void setBenificaryBranchNameAndAddress(String benificaryBranchNameAndAddress) {
            this.benificaryBranchNameAndAddress = benificaryBranchNameAndAddress;
        }

        public String getBenificaryBankCountry() {
            return benificaryBankCountry;
        }

        public void setBenificaryBankCountry(String benificaryBankCountry) {
            this.benificaryBankCountry = benificaryBankCountry;
        }

        public String getBenificaryBankName() {
            return benificaryBankName;
        }

        public void setBenificaryBankName(String benificaryBankName) {
            this.benificaryBankName = benificaryBankName;
        }

        public String getBenificaryPayOutBranchCode() {
            return benificaryPayOutBranchCode;
        }

        public void setBenificaryPayOutBranchCode(String benificaryPayOutBranchCode) {
            this.benificaryPayOutBranchCode = benificaryPayOutBranchCode;
        }

        public String getBenificaryPaymentMode() {
            return benificaryPaymentMode;
        }

        public void setBenificaryPaymentMode(String benificaryPaymentMode) {
            this.benificaryPaymentMode = benificaryPaymentMode;
        }

        public String getBenificaryPayOutCurrency() {
            return benificaryPayOutCurrency;
        }

        public void setBenificaryPayOutCurrency(String benificaryPayOutCurrency) {
            this.benificaryPayOutCurrency = benificaryPayOutCurrency;
        }

        public String getBenificaryIDType() {
            return benificaryIDType;
        }

        public void setBenificaryIDType(String benificaryIDType) {
            this.benificaryIDType = benificaryIDType;
        }

        public String getBenificaryIDNumber() {
            return benificaryIDNumber;
        }

        public void setBenificaryIDNumber(String benificaryIDNumber) {
            this.benificaryIDNumber = benificaryIDNumber;
        }

        public String getBenificaryNationality() {
            return benificaryNationality;
        }

        public void setBenificaryNationality(String benificaryNationality) {
            this.benificaryNationality = benificaryNationality;
        }

        public String getBenificaryDateOfBirth() {
            return benificaryDateOfBirth;
        }

        public void setBenificaryDateOfBirth(String benificaryDateOfBirth) {
            this.benificaryDateOfBirth = benificaryDateOfBirth;
        }

        public String getBenificaryTelephone() {
            return benificaryTelephone;
        }

        public void setBenificaryTelephone(String benificaryTelephone) {
            this.benificaryTelephone = benificaryTelephone;
        }

        public String getBenificaryEmailID() {
            return benificaryEmailID;
        }

        public void setBenificaryEmailID(String benificaryEmailID) {
            this.benificaryEmailID = benificaryEmailID;
        }

        public String getBenificaryZipCode() {
            return benificaryZipCode;
        }

        public void setBenificaryZipCode(String benificaryZipCode) {
            this.benificaryZipCode = benificaryZipCode;
        }

        public String getBenificaryLandMark() {
            return benificaryLandMark;
        }

        public void setBenificaryLandMark(String benificaryLandMark) {
            this.benificaryLandMark = benificaryLandMark;
        }

        public String getBenificaryMiddleName() {
            return benificaryMiddleName;
        }

        public void setBenificaryMiddleName(String benificaryMiddleName) {
            this.benificaryMiddleName = benificaryMiddleName;
        }

        public String getBenificaryNickName() {
            return benificaryNickName;
        }

        public void setBenificaryNickName(String benificaryNickName) {
            this.benificaryNickName = benificaryNickName;
        }

        public String getBenificaryLastName() {
            return benificaryLastName;
        }

        public void setBenificaryLastName(String benificaryLastName) {
            this.benificaryLastName = benificaryLastName;
        }

        public String getBenificaryAddress() {
            return benificaryAddress;
        }

        public void setBenificaryAddress(String benificaryAddress) {
            this.benificaryAddress = benificaryAddress;
        }

        public String getBenificaryFirstName() {
            return benificaryFirstName;
        }

        public void setBenificaryFirstName(String benificaryFirstName) {
            this.benificaryFirstName = benificaryFirstName;
        }

        public String getBenificaryCustomerNo() {
            return benificaryCustomerNo;
        }

        public void setBenificaryCustomerNo(String benificaryCustomerNo) {
            this.benificaryCustomerNo = benificaryCustomerNo;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getBenificaryID() {
            return benificaryID;
        }
        public String getBenificaryState() {
            return benificaryState;
        }
        public String getBenificaryCity() {
            return benificaryCity;
        }

        public void setBenificaryID(String benificaryID) {
            this.benificaryID = benificaryID;
        }
        public void setbenificaryCity(String benificaryCity) {
            this.benificaryCity = benificaryCity;
        }

        public void setBenificaryState(String benificaryState) {
            this.benificaryState = benificaryState;
        }
    }
}
