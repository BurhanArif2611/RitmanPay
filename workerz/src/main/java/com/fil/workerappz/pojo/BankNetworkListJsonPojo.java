package com.fil.workerappz.pojo;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class BankNetworkListJsonPojo implements Serializable {

    @Expose
    @SerializedName("info")
    private String info;
    @Expose
    @SerializedName("data")
    private List<Data> data;
    @Expose
    @SerializedName("status")
    private boolean status;



    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Data {
        @Expose
        @SerializedName("BankCode")
        private String BankCode;
        @Expose
        @SerializedName("BranchName")
        private String BranchName;
        @Expose
        @SerializedName("BankAddress")
        private String BankAddress;
        @Expose
        @SerializedName("BankName")
        private String BankName;

        public String getBankCode() {
            return BankCode;
        }

        public void setBankCode(String BankCode) {
            this.BankCode = BankCode;
        }

        public String getBranchName() {
            return BranchName;
        }

        public void setBranchName(String BranchName) {
            this.BranchName = BranchName;
        }

        public String getBankAddress() {
            return BankAddress;
        }

        public void setBankAddress(String BankAddress) {
            this.BankAddress = BankAddress;
        }

        public String getBankName() {
            return BankName;
        }

        public void setBankName(String BankName) {
            this.BankName = BankName;
        }
    }
}
