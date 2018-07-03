package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 05-Mar-18.
 * FIL AHM
 */

public class TransactionHistoryListJsonPojo implements Serializable {


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

    public static class Data {

        @Expose
        @SerializedName("userCountryCode")
        private String userCountryCode;
        @Expose
        @SerializedName("userMobile")
        private String userMobile;
        @Expose
        @SerializedName("userbankaccountName")
        private String userbankaccountName;
        @Expose
        @SerializedName("userLastname")
        private String userLastname;
        @Expose
        @SerializedName("userFirstName")
        private String userFirstName;
        @Expose
        @SerializedName("cronupdateDate")
        private String cronupdateDate;
        @Expose
        @SerializedName("croncount")
        private String croncount;
        @Expose
        @SerializedName("benificaryID")
        private String benificaryID;
        @Expose
        @SerializedName("transactionStatus")
        private String transactionStatus;
        @Expose
        @SerializedName("transactionDescription")
        private String transactionDescription;
        @Expose
        @SerializedName("thirdpartyServiceName")
        private String thirdpartyServiceName;
        @Expose
        @SerializedName("thirdpartyTransactionResponse")
        private String thirdpartyTransactionResponse;
        @Expose
        @SerializedName("thirdpartyTransactionRequest")
        private String thirdpartyTransactionRequest;
        @Expose
        @SerializedName("thirdpartyTransactionID")
        private String thirdpartyTransactionID;
        @Expose
        @SerializedName("userbankaccountID")
        private String userbankaccountID;
        @Expose
        @SerializedName("receiverWalletUserID")
        private String receiverWalletUserID;
        @Expose
        @SerializedName("transactionCurrency")
        private String transactionCurrency;
        @Expose
        @SerializedName("transactionAmount")
        private String transactionAmount;
        @Expose
        @SerializedName("transactionMode")
        private String transactionMode;
        @Expose
        @SerializedName("transactionUtilityInfo")
        private String transactionUtilityInfo;
        @Expose
        @SerializedName("transactionType")
        private String transactionType;
        @Expose
        @SerializedName("transactionDate")
        private String transactionDate;
        @Expose
        @SerializedName("userID")
        private String userID;
        @Expose
        @SerializedName("transactionID")
        private String transactionID;

        public String getUserCountryCode() {
            return userCountryCode;
        }

        public void setUserCountryCode(String userCountryCode) {
            this.userCountryCode = userCountryCode;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public String getUserbankaccountName() {
            return userbankaccountName;
        }

        public void setUserbankaccountName(String userbankaccountName) {
            this.userbankaccountName = userbankaccountName;
        }

        public String getUserLastname() {
            return userLastname;
        }

        public void setUserLastname(String userLastname) {
            this.userLastname = userLastname;
        }

        public String getUserFirstName() {
            return userFirstName;
        }

        public void setUserFirstName(String userFirstName) {
            this.userFirstName = userFirstName;
        }

        public String getCronupdateDate() {
            return cronupdateDate;
        }

        public void setCronupdateDate(String cronupdateDate) {
            this.cronupdateDate = cronupdateDate;
        }

        public String getCroncount() {
            return croncount;
        }

        public void setCroncount(String croncount) {
            this.croncount = croncount;
        }

        public String getBenificaryID() {
            return benificaryID;
        }

        public void setBenificaryID(String benificaryID) {
            this.benificaryID = benificaryID;
        }

        public String getTransactionStatus() {
            return transactionStatus;
        }

        public void setTransactionStatus(String transactionStatus) {
            this.transactionStatus = transactionStatus;
        }

        public String getTransactionDescription() {
            return transactionDescription;
        }

        public void setTransactionDescription(String transactionDescription) {
            this.transactionDescription = transactionDescription;
        }

        public String getThirdpartyServiceName() {
            return thirdpartyServiceName;
        }

        public void setThirdpartyServiceName(String thirdpartyServiceName) {
            this.thirdpartyServiceName = thirdpartyServiceName;
        }

        public String getThirdpartyTransactionResponse() {
            return thirdpartyTransactionResponse;
        }

        public void setThirdpartyTransactionResponse(String thirdpartyTransactionResponse) {
            this.thirdpartyTransactionResponse = thirdpartyTransactionResponse;
        }

        public String getThirdpartyTransactionRequest() {
            return thirdpartyTransactionRequest;
        }

        public void setThirdpartyTransactionRequest(String thirdpartyTransactionRequest) {
            this.thirdpartyTransactionRequest = thirdpartyTransactionRequest;
        }

        public String getThirdpartyTransactionID() {
            return thirdpartyTransactionID;
        }

        public void setThirdpartyTransactionID(String thirdpartyTransactionID) {
            this.thirdpartyTransactionID = thirdpartyTransactionID;
        }

        public String getUserbankaccountID() {
            return userbankaccountID;
        }

        public void setUserbankaccountID(String userbankaccountID) {
            this.userbankaccountID = userbankaccountID;
        }

        public String getReceiverWalletUserID() {
            return receiverWalletUserID;
        }

        public void setReceiverWalletUserID(String receiverWalletUserID) {
            this.receiverWalletUserID = receiverWalletUserID;
        }

        public String getTransactionCurrency() {
            return transactionCurrency;
        }

        public void setTransactionCurrency(String transactionCurrency) {
            this.transactionCurrency = transactionCurrency;
        }

        public String getTransactionAmount() {
            return transactionAmount;
        }

        public void setTransactionAmount(String transactionAmount) {
            this.transactionAmount = transactionAmount;
        }

        public String getTransactionMode() {
            return transactionMode;
        }

        public void setTransactionMode(String transactionMode) {
            this.transactionMode = transactionMode;
        }

        public String getTransactionUtilityInfo() {
            return transactionUtilityInfo;
        }

        public void setTransactionUtilityInfo(String transactionUtilityInfo) {
            this.transactionUtilityInfo = transactionUtilityInfo;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getTransactionID() {
            return transactionID;
        }

        public void setTransactionID(String transactionID) {
            this.transactionID = transactionID;
        }
    }
}
