package com.fil.workerappz.pojo;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class BankNetworkListJsonPojo implements Serializable {


    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("info")
    @Expose
    private String info;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

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

    public  class Data {

        @SerializedName("BankName")
        @Expose
        private String bankName;
        @SerializedName("BankAddress")
        @Expose
        private String bankAddress;
        @SerializedName("BranchName")
        @Expose
        private String branchName;
        @SerializedName("BankCode")
        @Expose
        private String bankCode;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankAddress() {
            return bankAddress;
        }

        public void setBankAddress(String bankAddress) {
            this.bankAddress = bankAddress;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

    }
}
