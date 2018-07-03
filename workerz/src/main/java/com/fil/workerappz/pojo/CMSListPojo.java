package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 01-Mar-18.
 * FIL AHM
 */

public class CMSListPojo implements Serializable {

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
        @SerializedName("cmspageScoUrl")
        private String cmspageScoUrl;
        @Expose
        @SerializedName("cmspageID")
        private int cmspageID;

        public String getCmspageScoUrl() {
            return cmspageScoUrl;
        }

        public void setCmspageScoUrl(String cmspageScoUrl) {
            this.cmspageScoUrl = cmspageScoUrl;
        }

        public int getCmspageID() {
            return cmspageID;
        }

        public void setCmspageID(int cmspageID) {
            this.cmspageID = cmspageID;
        }
    }
}
