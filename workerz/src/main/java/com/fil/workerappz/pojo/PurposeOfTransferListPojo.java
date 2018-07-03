package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class PurposeOfTransferListPojo implements Serializable {


    @Expose
    @SerializedName("status")
    private boolean status;
    @Expose
    @SerializedName("info")
    private String info;
    @Expose
    @SerializedName("data")
    private List<Data> data;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        @Expose
        @SerializedName("PurposeOfTranfer")
        private String PurposeOfTranfer;
        @Expose
        @SerializedName("PurposeOfTransferID")
        private String PurposeOfTransferID;

        public String getPurposeOfTranfer() {
            return PurposeOfTranfer;
        }

        public void setPurposeOfTranfer(String PurposeOfTranfer) {
            this.PurposeOfTranfer = PurposeOfTranfer;
        }

        public String getPurposeOfTransferID() {
            return PurposeOfTransferID;
        }

        public void setPurposeOfTransferID(String PurposeOfTransferID) {
            this.PurposeOfTransferID = PurposeOfTransferID;
        }
    }
}
