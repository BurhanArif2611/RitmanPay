package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class CashNetworkListJsonPojo implements Serializable {


    @Expose
    @SerializedName("info")
    private String info;
    @Expose
    @SerializedName("data")
    private List<Data> data;
    @Expose
    @SerializedName("status")
    private boolean status;

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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Data {
        @Expose
        @SerializedName("Telephone")
        private String Telephone;
        @Expose
        @SerializedName("Address")
        private Object Address;
        @Expose
        @SerializedName("BranchName")
        private String BranchName;
        @Expose
        @SerializedName("Location")
        private String Location;
        @Expose
        @SerializedName("CityCode")
        private String CityCode;
        @Expose
        @SerializedName("City")
        private String City;
        @Expose
        @SerializedName("PaymentMode")
        private String PaymentMode;
        @Expose
        @SerializedName("PayOutCurrencyCode")
        private String PayOutCurrencyCode;
        @Expose
        @SerializedName("PayOutBranchCode")
        private String PayOutBranchCode;
        @Expose
        @SerializedName("CountryCode")
        private String CountryCode;
        @Expose
        @SerializedName("Country")
        private String Country;
        @Expose
        @SerializedName("PayOutAgent")
        private String PayOutAgent;

        public String getTelephone() {
            return Telephone;
        }

        public void setTelephone(String Telephone) {
            this.Telephone = Telephone;
        }

        public Object getAddress() {
            return Address;
        }

        public void setAddress(Object Address) {
            if (this.Address.equals("[]")) {
                this.Address = "";
            }
            this.Address = Address;
        }

        public String getBranchName() {
            return BranchName;
        }

        public void setBranchName(String BranchName) {
            this.BranchName = BranchName;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String Location) {
            this.Location = Location;
        }

        public String getCityCode() {
            return CityCode;
        }

        public void setCityCode(String CityCode) {
            this.CityCode = CityCode;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getPaymentMode() {
            return PaymentMode;
        }

        public void setPaymentMode(String PaymentMode) {
            this.PaymentMode = PaymentMode;
        }

        public String getPayOutCurrencyCode() {
            return PayOutCurrencyCode;
        }

        public void setPayOutCurrencyCode(String PayOutCurrencyCode) {
            this.PayOutCurrencyCode = PayOutCurrencyCode;
        }

        public String getPayOutBranchCode() {
            return PayOutBranchCode;
        }

        public void setPayOutBranchCode(String PayOutBranchCode) {
            this.PayOutBranchCode = PayOutBranchCode;
        }

        public String getCountryCode() {
            return CountryCode;
        }

        public void setCountryCode(String CountryCode) {
            this.CountryCode = CountryCode;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String Country) {
            this.Country = Country;
        }

        public String getPayOutAgent() {
            return PayOutAgent;
        }

        public void setPayOutAgent(String PayOutAgent) {
            this.PayOutAgent = PayOutAgent;
        }
    }
}