package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class CalculateTransferListJsonPojo implements Serializable {


    @Expose
    @SerializedName("status")
    private boolean status;
    @Expose
    @SerializedName("info")
    private String info;
    @Expose
    @SerializedName("data")
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @Expose
        @SerializedName("PayoutBranchCode")
        private String PayoutBranchCode;
        @Expose
        @SerializedName("IsBanking")
        private String IsBanking;
        @Expose
        @SerializedName("RecommendAgent")
        private int RecommendAgent;
        @Expose
        @SerializedName("Description")
        private String Description;
        @Expose
        @SerializedName("ResponseCode")
        private int ResponseCode;
        @Expose
        @SerializedName("VATPercentage")
        private String VATPercentage;
        @Expose
        @SerializedName("VATValue")
        private String VATValue;
        @Expose
        @SerializedName("TotalPayable")
        private String TotalPayable;
        @Expose
        @SerializedName("PayoutAmount")
        private String PayoutAmount;
        @Expose
        @SerializedName("PayInAmount")
        private String PayInAmount;
        @Expose
        @SerializedName("PayoutCurrency")
        private String PayoutCurrency;
        @Expose
        @SerializedName("PayInCurrency")
        private String PayInCurrency;
        @Expose
        @SerializedName("Commission")
        private String Commission;
        @Expose
        @SerializedName("ExchangeRate")
        private String ExchangeRate;

        public String getPayoutBranchCode() {
            return PayoutBranchCode;
        }

        public void setPayoutBranchCode(String PayoutBranchCode) {
            this.PayoutBranchCode = PayoutBranchCode;
        }

        public String getIsBanking() {
            return IsBanking;
        }

        public void setIsBanking(String IsBanking) {
            this.IsBanking = IsBanking;
        }

        public int getRecommendAgent() {
            return RecommendAgent;
        }

        public void setRecommendAgent(int RecommendAgent) {
            this.RecommendAgent = RecommendAgent;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public int getResponseCode() {
            return ResponseCode;
        }

        public void setResponseCode(int ResponseCode) {
            this.ResponseCode = ResponseCode;
        }

        public String getVATPercentage() {
            return VATPercentage;
        }

        public void setVATPercentage(String VATPercentage) {
            this.VATPercentage = VATPercentage;
        }

        public String getVATValue() {
            return VATValue;
        }

        public void setVATValue(String VATValue) {
            this.VATValue = VATValue;
        }

        public String getTotalPayable() {
            return TotalPayable;
        }

        public void setTotalPayable(String TotalPayable) {
            this.TotalPayable = TotalPayable;
        }

        public String getPayoutAmount() {
            return PayoutAmount;
        }

        public void setPayoutAmount(String PayoutAmount) {
            this.PayoutAmount = PayoutAmount;
        }

        public String getPayInAmount() {
            return PayInAmount;
        }

        public void setPayInAmount(String PayInAmount) {
            this.PayInAmount = PayInAmount;
        }

        public String getPayoutCurrency() {
            return PayoutCurrency;
        }

        public void setPayoutCurrency(String PayoutCurrency) {
            this.PayoutCurrency = PayoutCurrency;
        }

        public String getPayInCurrency() {
            return PayInCurrency;
        }

        public void setPayInCurrency(String PayInCurrency) {
            this.PayInCurrency = PayInCurrency;
        }

        public String getCommission() {
            return Commission;
        }

        public void setCommission(String Commission) {
            this.Commission = Commission;
        }

        public String getExchangeRate() {
            return ExchangeRate;
        }

        public void setExchangeRate(String ExchangeRate) {
            this.ExchangeRate = ExchangeRate;
        }
    }
}
