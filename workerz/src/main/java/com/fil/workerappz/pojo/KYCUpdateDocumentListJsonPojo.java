package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FUSION on 28/03/2018.
 */

public class KYCUpdateDocumentListJsonPojo implements Serializable {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("info")
    @Expose
    private List<Data> info = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Data> getInfo() {
        return info;
    }

    public void setInfo(List<Data> info) {
        this.info = info;
    }

    public class Data {

        @SerializedName("userkycID")
        @Expose
        private Integer userkycID;
        @SerializedName("kycdocnameID")
        @Expose
        private Integer kycdocnameID;
        @SerializedName("kycdoctypeID")
        @Expose
        private Integer kycdoctypeID;
        @SerializedName("userID")
        @Expose
        private Integer userID;
        @SerializedName("userkycImage")
        @Expose
        private String userkycImage;
        @SerializedName("userkycStatus")
        @Expose
        private String userkycStatus;
        @SerializedName("userkycStatusReason")
        @Expose
        private String userkycStatusReason;
        @SerializedName("userkycStatusDate")
        @Expose
        private String userkycStatusDate;
        @SerializedName("userkycCreatedDate")
        @Expose
        private String userkycCreatedDate;
        @SerializedName("kycstatushistory")
        @Expose
        private String kycstatushistory;

        public Integer getUserkycID() {
            return userkycID;
        }

        public void setUserkycID(Integer userkycID) {
            this.userkycID = userkycID;
        }

        public Integer getKycdocnameID() {
            return kycdocnameID;
        }

        public void setKycdocnameID(Integer kycdocnameID) {
            this.kycdocnameID = kycdocnameID;
        }

        public Integer getKycdoctypeID() {
            return kycdoctypeID;
        }

        public void setKycdoctypeID(Integer kycdoctypeID) {
            this.kycdoctypeID = kycdoctypeID;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public String getUserkycImage() {
            return userkycImage;
        }

        public void setUserkycImage(String userkycImage) {
            this.userkycImage = userkycImage;
        }

        public String getUserkycStatus() {
            return userkycStatus;
        }

        public void setUserkycStatus(String userkycStatus) {
            this.userkycStatus = userkycStatus;
        }

        public String getUserkycStatusReason() {
            return userkycStatusReason;
        }

        public void setUserkycStatusReason(String userkycStatusReason) {
            this.userkycStatusReason = userkycStatusReason;
        }

        public String getUserkycStatusDate() {
            return userkycStatusDate;
        }

        public void setUserkycStatusDate(String userkycStatusDate) {
            this.userkycStatusDate = userkycStatusDate;
        }

        public String getUserkycCreatedDate() {
            return userkycCreatedDate;
        }

        public void setUserkycCreatedDate(String userkycCreatedDate) {
            this.userkycCreatedDate = userkycCreatedDate;
        }

        public String getKycstatushistory() {
            return kycstatushistory;
        }

        public void setKycstatushistory(String kycstatushistory) {
            this.kycstatushistory = kycstatushistory;
        }
    }
}
