package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class WalletToWalletTransferListPojo implements Serializable {


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
        @SerializedName("balance")
        private float balance;
        @Expose
        @SerializedName("transactionID")
        private int transactionID;

        public float getBalance() {
            return balance;
        }

        public void setBalance(float balance) {
            this.balance = balance;
        }

        public int getTransactionID() {
            return transactionID;
        }

        public void setTransactionID(int transactionID) {
            this.transactionID = transactionID;
        }
    }
}
