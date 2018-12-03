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

        @SerializedName("IDType_ID")
        @Expose
        private String iDTypeID;
        @SerializedName("IDType")
        @Expose
        private String iDType;
        @SerializedName("MinLength")
        @Expose
        private String minLength;
        @SerializedName("MaxLength")
        @Expose
        private String maxLength;
        @SerializedName("IsNumeric")
        @Expose
        private String isNumeric;
        @SerializedName("IsAlphaNumeric")
        @Expose
        private String isAlphaNumeric;
        @SerializedName("IsAddProof")
        @Expose
        private String isAddProof;
        @SerializedName("IsIDProof")
        @Expose
        private String isIDProof;

        public String getIDTypeID() {
            return iDTypeID;
        }

        public void setIDTypeID(String iDTypeID) {
            this.iDTypeID = iDTypeID;
        }

        public String getIDType() {
            return iDType;
        }

        public void setIDType(String iDType) {
            this.iDType = iDType;
        }

        public String getMinLength() {
            return minLength;
        }

        public void setMinLength(String minLength) {
            this.minLength = minLength;
        }

        public String getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(String maxLength) {
            this.maxLength = maxLength;
        }

        public String getIsNumeric() {
            return isNumeric;
        }

        public void setIsNumeric(String isNumeric) {
            this.isNumeric = isNumeric;
        }

        public String getIsAlphaNumeric() {
            return isAlphaNumeric;
        }

        public void setIsAlphaNumeric(String isAlphaNumeric) {
            this.isAlphaNumeric = isAlphaNumeric;
        }

        public String getIsAddProof() {
            return isAddProof;
        }

        public void setIsAddProof(String isAddProof) {
            this.isAddProof = isAddProof;
        }

        public String getIsIDProof() {
            return isIDProof;
        }

        public void setIsIDProof(String isIDProof) {
            this.isIDProof = isIDProof;
        }

    }
}