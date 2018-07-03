package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 19-Mar-18.
 * FIL AHM
 */

public class CreateBeneficiaryJsonPojo implements Serializable {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("info")
    @Expose
    private String info;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

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
    public class Data {

        @SerializedName("CustomerNumber")
        @Expose
        private String customerNumber;
        @SerializedName("BeneficiaryNo")
        @Expose
        private String beneficiaryNo;
        @SerializedName("ResponseCode")
        @Expose
        private Integer responseCode;
        @SerializedName("Description")
        @Expose
        private String description;

        public String getCustomerNumber() {
            return customerNumber;
        }

        public void setCustomerNumber(String customerNumber) {
            this.customerNumber = customerNumber;
        }

        public String getBeneficiaryNo() {
            return beneficiaryNo;
        }

        public void setBeneficiaryNo(String beneficiaryNo) {
            this.beneficiaryNo = beneficiaryNo;
        }

        public Integer getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(Integer responseCode) {
            this.responseCode = responseCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }
}