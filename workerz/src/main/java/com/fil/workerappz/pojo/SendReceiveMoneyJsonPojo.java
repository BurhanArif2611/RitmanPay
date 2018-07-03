package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by FUSION on 05/04/2018.
 */

public class SendReceiveMoneyJsonPojo implements Serializable {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public class Data {

        @SerializedName("ExchangeRate")
        @Expose
        private String exchangeRate;
        @SerializedName("Commission")
        @Expose
        private String commission;
        @SerializedName("PayInCurrency")
        @Expose
        private String payInCurrency;
        @SerializedName("PayoutCurrency")
        @Expose
        private String payoutCurrency;
        @SerializedName("PayInAmount")
        @Expose
        private String payInAmount;
        @SerializedName("PayoutAmount")
        @Expose
        private String payoutAmount;
        @SerializedName("TotalPayable")
        @Expose
        private String totalPayable;
        @SerializedName("VATValue")
        @Expose
        private String vATValue;
        @SerializedName("VATPercentage")
        @Expose
        private String vATPercentage;
        @SerializedName("ResponseCode")
        @Expose
        private Integer responseCode;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("RecommendAgent")
        @Expose
        private Integer recommendAgent;
        @SerializedName("IsBanking")
        @Expose
        private String isBanking;
        @SerializedName("PayoutBranchCode")
        @Expose
        private String payoutBranchCode;

        public String getExchangeRate() {
            return exchangeRate;
        }

        public void setExchangeRate(String exchangeRate) {
            this.exchangeRate = exchangeRate;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getPayInCurrency() {
            return payInCurrency;
        }

        public void setPayInCurrency(String payInCurrency) {
            this.payInCurrency = payInCurrency;
        }

        public String getPayoutCurrency() {
            return payoutCurrency;
        }

        public void setPayoutCurrency(String payoutCurrency) {
            this.payoutCurrency = payoutCurrency;
        }

        public String getPayInAmount() {
            return payInAmount;
        }

        public void setPayInAmount(String payInAmount) {
            this.payInAmount = payInAmount;
        }

        public String getPayoutAmount() {
            return payoutAmount;
        }

        public void setPayoutAmount(String payoutAmount) {
            this.payoutAmount = payoutAmount;
        }

        public String getTotalPayable() {
            return totalPayable;
        }

        public void setTotalPayable(String totalPayable) {
            this.totalPayable = totalPayable;
        }

        public String getVATValue() {
            return vATValue;
        }

        public void setVATValue(String vATValue) {
            this.vATValue = vATValue;
        }

        public String getVATPercentage() {
            return vATPercentage;
        }

        public void setVATPercentage(String vATPercentage) {
            this.vATPercentage = vATPercentage;
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

        public Integer getRecommendAgent() {
            return recommendAgent;
        }

        public void setRecommendAgent(Integer recommendAgent) {
            this.recommendAgent = recommendAgent;
        }

        public String getIsBanking() {
            return isBanking;
        }

        public void setIsBanking(String isBanking) {
            this.isBanking = isBanking;
        }

        public String getPayoutBranchCode() {
            return payoutBranchCode;
        }

        public void setPayoutBranchCode(String payoutBranchCode) {
            this.payoutBranchCode = payoutBranchCode;
        }

    }
}

