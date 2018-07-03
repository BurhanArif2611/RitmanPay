package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by FUSION on 06/04/2018.
 */

public class SendMoneyBeneficiaryJsonPojo implements Serializable {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("balance")
    @Expose
    private Double balance;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public class Data {

        @SerializedName("RPTxnNo")
        @Expose
        private String rPTxnNo;
        @SerializedName("ClientTxnNO")
        @Expose
        private String clientTxnNO;
        @SerializedName("CustomerNo")
        @Expose
        private String customerNo;
        @SerializedName("BeneficiaryNo")
        @Expose
        private String beneficiaryNo;
        @SerializedName("ResponseCode")
        @Expose
        private Integer responseCode;
        @SerializedName("Description")
        @Expose
        private String description;

        public String getRPTxnNo() {
            return rPTxnNo;
        }

        public void setRPTxnNo(String rPTxnNo) {
            this.rPTxnNo = rPTxnNo;
        }

        public String getClientTxnNO() {
            return clientTxnNO;
        }

        public void setClientTxnNO(String clientTxnNO) {
            this.clientTxnNO = clientTxnNO;
        }

        public String getCustomerNo() {
            return customerNo;
        }

        public void setCustomerNo(String customerNo) {
            this.customerNo = customerNo;
        }

        public String getBeneficiaryNo() {
            return beneficiaryNo;
        }

        public void setBeneficiaryNo(String beneficiaryNo) {
            this.beneficiaryNo = beneficiaryNo;
        }

        public Integer getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(Integer responseCode) {
            this.responseCode = responseCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

}
