package com.fil.workerappz.pojo.ding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 22-Mar-18.
 * FIL AHM
 */

public class GetProductsList implements Serializable {

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
        @SerializedName("RegionCode")
        private String RegionCode;
        @Expose
        @SerializedName("DefaultDisplayText")
        private String DefaultDisplayText;
        @Expose
        @SerializedName("AdditionalInformation")
        private String AdditionalInformation;
        @Expose
        @SerializedName("UatNumber")
        private String UatNumber;
        @Expose
        @SerializedName("ValidityPeriodIso")
        private String ValidityPeriodIso;
        @Expose
        @SerializedName("Benefits")
        private List<String> Benefits;
        @Expose
        @SerializedName("RedemptionMechanism")
        private String RedemptionMechanism;
        @Expose
        @SerializedName("ProcessingMode")
        private String ProcessingMode;
        @Expose
        @SerializedName("CommissionRate")
        private int CommissionRate;
        @Expose
        @SerializedName("Minimum")
        private Minimum Minimum;
        @Expose
        @SerializedName("Maximum")
        private Maximum Maximum;
        @Expose
        @SerializedName("SettingDefinitions")
        private List<String> SettingDefinitions;
        @Expose
        @SerializedName("LocalizationKey")
        private String LocalizationKey;
        @Expose
        @SerializedName("SkuCode")
        private String SkuCode;
        @Expose
        @SerializedName("ProviderCode")
        private String ProviderCode;

        public String getRegionCode() {
            return RegionCode;
        }

        public void setRegionCode(String RegionCode) {
            this.RegionCode = RegionCode;
        }

        public String getDefaultDisplayText() {
            return DefaultDisplayText;
        }

        public void setDefaultDisplayText(String DefaultDisplayText) {
            this.DefaultDisplayText = DefaultDisplayText;
        }

        public String getAdditionalInformation() {
            return AdditionalInformation;
        }

        public void setAdditionalInformation(String AdditionalInformation) {
            this.AdditionalInformation = AdditionalInformation;
        }

        public String getUatNumber() {
            return UatNumber;
        }

        public void setUatNumber(String UatNumber) {
            this.UatNumber = UatNumber;
        }

        public String getValidityPeriodIso() {
            return ValidityPeriodIso;
        }

        public void setValidityPeriodIso(String ValidityPeriodIso) {
            this.ValidityPeriodIso = ValidityPeriodIso;
        }

        public List<String> getBenefits() {
            return Benefits;
        }

        public void setBenefits(List<String> Benefits) {
            this.Benefits = Benefits;
        }

        public String getRedemptionMechanism() {
            return RedemptionMechanism;
        }

        public void setRedemptionMechanism(String RedemptionMechanism) {
            this.RedemptionMechanism = RedemptionMechanism;
        }

        public String getProcessingMode() {
            return ProcessingMode;
        }

        public void setProcessingMode(String ProcessingMode) {
            this.ProcessingMode = ProcessingMode;
        }

        public int getCommissionRate() {
            return CommissionRate;
        }

        public void setCommissionRate(int CommissionRate) {
            this.CommissionRate = CommissionRate;
        }

        public Minimum getMinimum() {
            return Minimum;
        }

        public void setMinimum(Minimum Minimum) {
            this.Minimum = Minimum;
        }

        public Maximum getMaximum() {
            return Maximum;
        }

        public void setMaximum(Maximum Maximum) {
            this.Maximum = Maximum;
        }

        public List<String> getSettingDefinitions() {
            return SettingDefinitions;
        }

        public void setSettingDefinitions(List<String> SettingDefinitions) {
            this.SettingDefinitions = SettingDefinitions;
        }

        public String getLocalizationKey() {
            return LocalizationKey;
        }

        public void setLocalizationKey(String LocalizationKey) {
            this.LocalizationKey = LocalizationKey;
        }

        public String getSkuCode() {
            return SkuCode;
        }

        public void setSkuCode(String SkuCode) {
            this.SkuCode = SkuCode;
        }

        public String getProviderCode() {
            return ProviderCode;
        }

        public void setProviderCode(String ProviderCode) {
            this.ProviderCode = ProviderCode;
        }
    }

    public static class Minimum {
        @Expose
        @SerializedName("SendCurrencyIso")
        private String SendCurrencyIso;
        @Expose
        @SerializedName("SendValue")
        private double SendValue;
        @Expose
        @SerializedName("TaxCalculation")
        private String TaxCalculation;
        @Expose
        @SerializedName("TaxName")
        private String TaxName;
        @Expose
        @SerializedName("TaxRate")
        private int TaxRate;
        @Expose
        @SerializedName("ReceiveValueExcludingTax")
        private int ReceiveValueExcludingTax;
        @Expose
        @SerializedName("ReceiveCurrencyIso")
        private String ReceiveCurrencyIso;
        @Expose
        @SerializedName("ReceiveValue")
        private int ReceiveValue;
        @Expose
        @SerializedName("DistributorFee")
        private int DistributorFee;
        @Expose
        @SerializedName("CustomerFee")
        private int CustomerFee;

        public String getSendCurrencyIso() {
            return SendCurrencyIso;
        }

        public void setSendCurrencyIso(String SendCurrencyIso) {
            this.SendCurrencyIso = SendCurrencyIso;
        }

        public double getSendValue() {
            return SendValue;
        }

        public void setSendValue(double SendValue) {
            this.SendValue = SendValue;
        }

        public String getTaxCalculation() {
            return TaxCalculation;
        }

        public void setTaxCalculation(String TaxCalculation) {
            this.TaxCalculation = TaxCalculation;
        }

        public String getTaxName() {
            return TaxName;
        }

        public void setTaxName(String TaxName) {
            this.TaxName = TaxName;
        }

        public int getTaxRate() {
            return TaxRate;
        }

        public void setTaxRate(int TaxRate) {
            this.TaxRate = TaxRate;
        }

        public int getReceiveValueExcludingTax() {
            return ReceiveValueExcludingTax;
        }

        public void setReceiveValueExcludingTax(int ReceiveValueExcludingTax) {
            this.ReceiveValueExcludingTax = ReceiveValueExcludingTax;
        }

        public String getReceiveCurrencyIso() {
            return ReceiveCurrencyIso;
        }

        public void setReceiveCurrencyIso(String ReceiveCurrencyIso) {
            this.ReceiveCurrencyIso = ReceiveCurrencyIso;
        }

        public int getReceiveValue() {
            return ReceiveValue;
        }

        public void setReceiveValue(int ReceiveValue) {
            this.ReceiveValue = ReceiveValue;
        }

        public int getDistributorFee() {
            return DistributorFee;
        }

        public void setDistributorFee(int DistributorFee) {
            this.DistributorFee = DistributorFee;
        }

        public int getCustomerFee() {
            return CustomerFee;
        }

        public void setCustomerFee(int CustomerFee) {
            this.CustomerFee = CustomerFee;
        }
    }

    public static class Maximum {
        @Expose
        @SerializedName("SendCurrencyIso")
        private String SendCurrencyIso;
        @Expose
        @SerializedName("SendValue")
        private double SendValue;
        @Expose
        @SerializedName("TaxCalculation")
        private String TaxCalculation;
        @Expose
        @SerializedName("TaxName")
        private String TaxName;
        @Expose
        @SerializedName("TaxRate")
        private int TaxRate;
        @Expose
        @SerializedName("ReceiveValueExcludingTax")
        private int ReceiveValueExcludingTax;
        @Expose
        @SerializedName("ReceiveCurrencyIso")
        private String ReceiveCurrencyIso;
        @Expose
        @SerializedName("ReceiveValue")
        private int ReceiveValue;
        @Expose
        @SerializedName("DistributorFee")
        private int DistributorFee;
        @Expose
        @SerializedName("CustomerFee")
        private int CustomerFee;

        public String getSendCurrencyIso() {
            return SendCurrencyIso;
        }

        public void setSendCurrencyIso(String SendCurrencyIso) {
            this.SendCurrencyIso = SendCurrencyIso;
        }

        public double getSendValue() {
            return SendValue;
        }

        public void setSendValue(double SendValue) {
            this.SendValue = SendValue;
        }

        public String getTaxCalculation() {
            return TaxCalculation;
        }

        public void setTaxCalculation(String TaxCalculation) {
            this.TaxCalculation = TaxCalculation;
        }

        public String getTaxName() {
            return TaxName;
        }

        public void setTaxName(String TaxName) {
            this.TaxName = TaxName;
        }

        public int getTaxRate() {
            return TaxRate;
        }

        public void setTaxRate(int TaxRate) {
            this.TaxRate = TaxRate;
        }

        public int getReceiveValueExcludingTax() {
            return ReceiveValueExcludingTax;
        }

        public void setReceiveValueExcludingTax(int ReceiveValueExcludingTax) {
            this.ReceiveValueExcludingTax = ReceiveValueExcludingTax;
        }

        public String getReceiveCurrencyIso() {
            return ReceiveCurrencyIso;
        }

        public void setReceiveCurrencyIso(String ReceiveCurrencyIso) {
            this.ReceiveCurrencyIso = ReceiveCurrencyIso;
        }

        public int getReceiveValue() {
            return ReceiveValue;
        }

        public void setReceiveValue(int ReceiveValue) {
            this.ReceiveValue = ReceiveValue;
        }

        public int getDistributorFee() {
            return DistributorFee;
        }

        public void setDistributorFee(int DistributorFee) {
            this.DistributorFee = DistributorFee;
        }

        public int getCustomerFee() {
            return CustomerFee;
        }

        public void setCustomerFee(int CustomerFee) {
            this.CustomerFee = CustomerFee;
        }
    }
}