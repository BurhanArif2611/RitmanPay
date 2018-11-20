package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModeWiseCountryListJsonPojo implements Serializable {
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

        @SerializedName("CountryName")
        @Expose
        private String countryName;
        @SerializedName("CountryShortName")
        @Expose
        private String countryShortName;

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryShortName() {
            return countryShortName;
        }

        public void setCountryShortName(String countryShortName) {
            this.countryShortName = countryShortName;
        }
    }
}
