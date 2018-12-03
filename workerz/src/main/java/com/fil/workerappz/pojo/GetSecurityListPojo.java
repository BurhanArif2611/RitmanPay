package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetSecurityListPojo implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("data")
    @Expose
    private List<DataSecurityList> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<DataSecurityList> getData() {
        return data;
    }

    public void setData(List<DataSecurityList> data) {
        this.data = data;
    }

    public class DataSecurityList {

        @SerializedName("secID")
        @Expose
        private String secID;
        @SerializedName("secQuestion")
        @Expose
        private String secQuestion;

        public String getSecID() {
            return secID;
        }

        public void setSecID(String secID) {
            this.secID = secID;
        }

        public String getSecQuestion() {
            return secQuestion;
        }

        public void setSecQuestion(String secQuestion) {
            this.secQuestion = secQuestion;
        }
    }
}
