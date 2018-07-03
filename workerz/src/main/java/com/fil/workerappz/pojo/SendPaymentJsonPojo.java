package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class SendPaymentJsonPojo implements Serializable {


    @Expose
    @SerializedName("balance")
    private int balance;
    @Expose
    @SerializedName("status")
    private boolean status;
    @Expose
    @SerializedName("info")
    private String info;
    @Expose
    @SerializedName("data")
    private Data data;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @Expose
        @SerializedName("Description")
        private String Description;
        @Expose
        @SerializedName("ResponseCode")
        private int ResponseCode;
        @Expose
        @SerializedName("BeneficiaryNo")
        private String BeneficiaryNo;
        @Expose
        @SerializedName("CustomerNo")
        private String CustomerNo;
        @Expose
        @SerializedName("ClientTxnNO")
        private String ClientTxnNO;
        @Expose
        @SerializedName("RPTxnNo")
        private String RPTxnNo;

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public int getResponseCode() {
            return ResponseCode;
        }

        public void setResponseCode(int ResponseCode) {
            this.ResponseCode = ResponseCode;
        }

        public String getBeneficiaryNo() {
            return BeneficiaryNo;
        }

        public void setBeneficiaryNo(String BeneficiaryNo) {
            this.BeneficiaryNo = BeneficiaryNo;
        }

        public String getCustomerNo() {
            return CustomerNo;
        }

        public void setCustomerNo(String CustomerNo) {
            this.CustomerNo = CustomerNo;
        }

        public String getClientTxnNO() {
            return ClientTxnNO;
        }

        public void setClientTxnNO(String ClientTxnNO) {
            this.ClientTxnNO = ClientTxnNO;
        }

        public String getRPTxnNo() {
            return RPTxnNo;
        }

        public void setRPTxnNo(String RPTxnNo) {
            this.RPTxnNo = RPTxnNo;
        }
    }
}
