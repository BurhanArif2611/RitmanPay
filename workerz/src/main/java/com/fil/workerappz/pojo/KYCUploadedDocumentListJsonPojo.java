package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 05-Mar-18.
 * FIL AHM
 */

public class KYCUploadedDocumentListJsonPojo implements Serializable {


    @Expose
    @SerializedName("info")
    private List<Info> info;
    @Expose
    @SerializedName("status")
    private boolean status;

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Info {
        @Expose
        @SerializedName("kycstatushistory")
        private String kycstatushistory;
        @Expose
        @SerializedName("userkycCreatedDate")
        private String userkycCreatedDate;
        @Expose
        @SerializedName("userkycStatusDate")
        private String userkycStatusDate;
        @Expose
        @SerializedName("userkycStatusReason")
        private String userkycStatusReason;
        @Expose
        @SerializedName("userkycStatus")
        private String userkycStatus;
        @Expose
        @SerializedName("userkycImage")
        private String userkycImage;
        @Expose
        @SerializedName("userID")
        private String userID;
        @Expose
        @SerializedName("kycdocnameName")
        private String kycdocnameName;
        @Expose
        @SerializedName("kycdocnameID")
        private String kycdocnameID;
        @Expose
        @SerializedName("userkycID")
        private String userkycID;
        @SerializedName("kycdoctypeID")
        @Expose
        private String kycdoctypeID;
        @Expose
        @SerializedName("kycdoctypeName")
        private String kycdoctypeName;
        public String getKycstatushistory() {
            return kycstatushistory;
        }

        public void setKycstatushistory(String kycstatushistory) {
            this.kycstatushistory = kycstatushistory;
        }

        public String getUserkycCreatedDate() {
            return userkycCreatedDate;
        }

        public void setUserkycCreatedDate(String userkycCreatedDate) {
            this.userkycCreatedDate = userkycCreatedDate;
        }

        public String getUserkycStatusDate() {
            return userkycStatusDate;
        }

        public void setUserkycStatusDate(String userkycStatusDate) {
            this.userkycStatusDate = userkycStatusDate;
        }

        public String getUserkycStatusReason() {
            return userkycStatusReason;
        }

        public void setUserkycStatusReason(String userkycStatusReason) {
            this.userkycStatusReason = userkycStatusReason;
        }

        public String getUserkycStatus() {
            return userkycStatus;
        }

        public void setUserkycStatus(String userkycStatus) {
            this.userkycStatus = userkycStatus;
        }

        public String getUserkycImage() {
            return userkycImage;
        }

        public void setUserkycImage(String userkycImage) {
            this.userkycImage = userkycImage;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getKycdocnameName() {
            return kycdocnameName;
        }

        public void setKycdocnameName(String kycdocnameName) {
            this.kycdocnameName = kycdocnameName;
        }

        public String getKycdocnameID() {
            return kycdocnameID;
        }

        public void setKycdocnameID(String kycdocnameID) {
            this.kycdocnameID = kycdocnameID;
        }

        public String getUserkycID() {
            return userkycID;
        }

        public void setUserkycID(String userkycID) {
            this.userkycID = userkycID;
        }
        public String getKycdoctypeID() {
            return kycdoctypeID;
        }

        public void setKycdoctypeID(String kycdoctypeID) {
            this.kycdoctypeID = kycdoctypeID;
        }
        public String getKycdoctypeName() {
            return kycdoctypeName;
        }

        public void setKycdoctypeName(String kycdoctypeName) {
            this.kycdoctypeName = kycdoctypeName;
        }

    }
}
