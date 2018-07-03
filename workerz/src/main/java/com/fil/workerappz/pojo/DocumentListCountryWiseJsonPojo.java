package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 05-Mar-18.
 * FIL AHM
 */

public class DocumentListCountryWiseJsonPojo implements Serializable {


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
        @SerializedName("kycdoctypeID")
        private String kycdoctypeID;
        @Expose
        @SerializedName("kycdocnameID")
        private String kycdocnameID;
        @Expose
        @SerializedName("countryName")
        private String countryName;
        @Expose
        @SerializedName("countryID")
        private String countryID;
        @Expose
        @SerializedName("kycdoctypeName")
        private String kycdoctypeName;
        @Expose
        @SerializedName("kycdocnameName")
        private String kycdocnameName;

        public String getKycdoctypeID() {
            return kycdoctypeID;
        }

        public void setKycdoctypeID(String kycdoctypeID) {
            this.kycdoctypeID = kycdoctypeID;
        }

        public String getKycdocnameID() {
            return kycdocnameID;
        }

        public void setKycdocnameID(String kycdocnameID) {
            this.kycdocnameID = kycdocnameID;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryID() {
            return countryID;
        }

        public void setCountryID(String countryID) {
            this.countryID = countryID;
        }

        public String getKycdoctypeName() {
            return kycdoctypeName;
        }

        public void setKycdoctypeName(String kycdoctypeName) {
            this.kycdoctypeName = kycdoctypeName;
        }

        public String getKycdocnameName() {
            return kycdocnameName;
        }

        public void setKycdocnameName(String kycdocnameName) {
            this.kycdocnameName = kycdocnameName;
        }
    }
}
