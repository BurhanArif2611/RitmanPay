package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class IdTypeListJsonPojo implements Serializable {


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
        @SerializedName("IDType")
        private String IDType;
        @Expose
        @SerializedName("IDType_ID")
        private String IDType_ID;

        public String getIDType() {
            return IDType;
        }

        public void setIDType(String IDType) {
            this.IDType = IDType;
        }

        public String getIDType_ID() {
            return IDType_ID;
        }

        public void setIDType_ID(String IDType_ID) {
            this.IDType_ID = IDType_ID;
        }
    }
}